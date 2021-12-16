package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior.ICWarsCell;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;
import java.util.Queue;
import java.util.Collections;
 
public abstract class Unit extends ICWarsActor implements Interactor,Interactable {
    
    protected Sprite sprite;

    protected String spriteName;
    protected int currentHP;
    protected int maxHP;
    protected int maxDamage;
    protected int radius;
    protected ICWarsRange range;
    protected DiscreteCoordinates coordinates;
    protected boolean used;
    protected int defenseStars;
    protected List<Action> actions;
    private final ICWarsUnitInteractionHandler handler;

    public Unit(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates, faction);
        this.sprite = new Sprite(this.getSpriteName(), 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.coordinates = coordinates;
        sprite.setDepth(0);
        handler = new ICWarsUnitInteractionHandler();
    }
    /***
     * Create range of a unit
     */
    public void createRange () {

        range = new ICWarsRange();

        int xPosition = (int)getPosition().x;
        int yPosition = (int)getPosition().y;
        int width = this.getOwnerArea().getWidth();
        int height = this.getOwnerArea().getHeight();
        boolean left,up,right,down;

        for (int fromX = -radius; fromX <= radius; fromX++) {
            for (int fromY = -radius; fromY  <= radius; fromY ++) {

                left = fromX > -radius && fromX+xPosition> 0;
                right = fromX < radius && fromX+xPosition < width-1;
                up = fromY + yPosition < height - 1 && fromY<radius;
                down = fromY + yPosition > 0 && fromY > -radius;

                if (xPosition + fromX >= 0 && xPosition + fromX < width && yPosition + fromY  >= 0 && yPosition + fromY  < height) {
                    this.range.addNode(new DiscreteCoordinates(fromX + xPosition, fromY  + yPosition), left, up, right, down);
                }
            }
        }
    }
    /**
     * Getter for coordinates
     * @return coordinates
     */
    public DiscreteCoordinates getCoordinates() {
        return coordinates;
    }
    
    public List<Action> getActions(){
    	return Collections.unmodifiableList(actions);
    }
    /**
     * Getter for radius
     * @return radius
     */
    public int getRadius() {
        return radius;
    }
    /**
     * Getter for maxDamage
     * @return maxDamage
     */
    public int getDamage() {
    	return maxDamage;
    };
    /**
     * Getter for currentHP
     * @return currentHP
     */
    public int getHP() {
    	return currentHP;
    }
    /**
     * Getter for spriteName
     * @return spriteName
     */
    public String getSpriteName() {
        return spriteName;
    }
    
    public abstract String getName();
    /**
     * Unit gets damaged by specified amount of HP
     * @param damage
     */
    public void damage(int damage){
        if ((currentHP - damage) < 0) {
            currentHP=0;
        } else {
            currentHP -= damage;
        }
    }
    /**
     * Unit gets repaired by specified amount of HP
     * @param repair
     */
    public void repair(int repair) {
        if ((currentHP += repair) > maxHP) {
            currentHP = maxHP;
        } else {
            currentHP += repair;
        }
    }
    /***
    Draw the unit's range and a path from the unit position to
    destination
    * @param destination path destination
    * @param canvas canvas
    */
    public void drawRangeAndPathTo(DiscreteCoordinates destination , Canvas canvas) {
    	range.draw(canvas);
    	Queue <Orientation > path = range.shortestPath(getCurrentMainCellCoordinates (),destination);
    	//Draw path only if it exists (destination inside the range)
	    if (path != null){
	    	new Path(getCurrentMainCellCoordinates ().toVector (),path).draw(canvas);
	    }
    }
    /**
     * Setter for used 
     * (sets whether a unit has been used and modifies its sprite accordingly)
     * @param used
     */
    public void setUsed(boolean used) {
        this.used=used;
        if(used == true){sprite.setAlpha(0.5f);} 
        else {sprite.setAlpha(1.f);}
    }
    /**
     * Getter for used (whether a unit has already been used)
     * @return used
     */
    public boolean getUsed(){return used;}
    
    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
    	if (range.nodeExists(newPosition) && super.changePosition(newPosition)) {
    		return true;
    	}
    	return false;
    }
    
	public int getDefense() {
		return defenseStars;
	}
    
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
    
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {return null;}

    @Override
    public boolean wantsCellInteraction() {return true;}

    @Override
    public boolean wantsViewInteraction() {return false;}

    @Override
    public boolean takeCellSpace() {return true;}

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarInteractionVisitor)v).interactWith(this);
    }
    
    @Override
	public void interactWith(Interactable other) {
		if (!isDisplacementOccurs()) {
			other.acceptInteraction(handler);
	    }
	}
    
    private class ICWarsUnitInteractionHandler implements ICWarInteractionVisitor {

		@Override
		public void interactWith(Cell cell) {
			defenseStars = ((ICWarsCell)cell).getDefense();
		}
	}


}