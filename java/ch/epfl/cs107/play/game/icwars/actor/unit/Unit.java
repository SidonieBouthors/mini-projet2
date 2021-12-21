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
    
    private Sprite sprite;
    private int currentHP;
    private int maxHP;
    private int maxDamage;
    private int maxHealing;
    private int radius;
    private int attackRadius;
    private int defenceStars;
    private boolean used;
    private ICWarsRange range;
    private List<Action> actions;
    private final ICWarsUnitInteractionHandler handler;

    /**
     * Default Unit Constructor
     * @param owner			(Area): owner area
     * @param coordinates	(DiscreteCoordinates): starting coordinates
     * @param faction		(Faction): faction of unit
     */
    public Unit(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates, faction);
        this.sprite = new Sprite(this.getSpriteName(), 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.sprite.setDepth(0);
        this.handler = new ICWarsUnitInteractionHandler();
    }
    /***
     * Create the range of a unit (ICWarsRange)
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
    
    /***
    Draw the unit's range and a path from the unit position to
    destination
    * @param destination	(DiscreteCoordinates): path destination
    * @param canvas 		(Canvas): canvas
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
     * Unit gets damaged by specified amount of HP
     * @param damage	(int): amount of HP to remove from unit
     */
    public void damage(int damage){
        if ((currentHP - damage) < 0) { currentHP=0; } 
        else { currentHP -= damage; }
    }
    /**
     * Unit gets repaired by specified amount of HP
     * @param repair	(int): amount of HP to add to unit
     */
    public void repair(int repair) {
        if ((currentHP + repair) > maxHP) { currentHP = maxHP; } 
        else { currentHP += repair; }
    }

    /**
     * Getter for actions
     * - Non intrusive getter: unmodifiableList(List) is immutable
     * @return actions
     */
    public List<Action> getActions(){
    	return Collections.unmodifiableList(actions);
    }
    /**
     * Getter for coordinates
     * - Non intrusive getter: DiscreteCoordinates is immutable
     * @return coordinates
     */
    public DiscreteCoordinates getCoordinates() {
    	return getCurrentCells().get(0);
    }
    /**
     * Getter for radius
     * @return radius
     */
    public int getRadius() {
        return radius;
    }
    /**
     * Getter for attack radius
     * @return radius
     */
    public int getAttackRadius() {
        return attackRadius;
    }
    /**
     * Getter for maxDamage
     * @return maxDamage
     */
    public int getDamage() {
    	return maxDamage;
    }
    /**
     * Getter for maxHealing
     * @return maxHealing
     */
    public int getHealing() {
    	return maxHealing;
    }
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
    public abstract String getSpriteName();
    /**
     * Getter for unit name
     * @return unitName	(String): unit name
     */
    public abstract String getName();
    /**
     * Getter for current defence of the Unit
     * @return defence	(int): number of defence stars currently protecting unit
     */
	public int getDefence() {
		return defenceStars;
	}
    /**
     * Getter for used (whether a unit has already been used)
     * @return used	(boolean): whether the unit is used
     */
    public boolean getUsed(){
    	return used;
    	}
    /**
     * Setter for used 
     * (sets whether a unit has been used and modifies its sprite accordingly)
     * @param used
     */
    public void setUsed(boolean used) {
        this.used=used;
        if(used){sprite.setAlpha(0.5f);}
        else {sprite.setAlpha(1.f);}
    }
    /**
     * Setter for actions (Protected)
     * @param actions	(List<Action>)
     */
    protected void setActions(List<Action> actions) {this.actions = actions;}
    /**
     * Setter for radius (Protected)
     * @param radius	(int)
     */
    protected void setRadius(int radius) { this.radius = radius;}
    /**
     * Setter for attack radius (Protected)
     * @param attackRadius	(int)
     */
    protected void setAttackRadius(int radius) { this.attackRadius = radius;}
    /**
     * Setter for max damage (Protected)
     * @param damage	(int)
     */
    protected void setMaxDamage(int damage) { this.maxDamage = damage;}
    /**
     * Setter for max healing (Protected)
     * @param healing	(int)
     */
    protected void setMaxHealing(int healing) { this.maxHealing = healing;}
    /**
     * Setter for max HP (Protected)
     * @param HP	(int)
     */
    protected void setMaxHP(int HP) { this.maxHP = HP;}
    /**
     * Setter to set currentHP to maxHP
     */
    protected void setCurrentHPToMax() {this.currentHP = maxHP; }

    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        return range.nodeExists(newPosition) && super.changePosition(newPosition);
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
    	if (currentHP != 0) {
    		((ICWarInteractionVisitor)v).interactWith(this);
    	}
    }
    
    @Override
	public void interactWith(Interactable other) {
		if (!isDisplacementOccurs() && currentHP!=0) {
			other.acceptInteraction(handler);
	    }
	}
    
    private class ICWarsUnitInteractionHandler implements ICWarInteractionVisitor {
		@Override
		public void interactWith(Cell cell) {
			defenceStars = ((ICWarsCell)cell).getDefense();
		}
	}
}
