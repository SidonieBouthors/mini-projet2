package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;
import java.util.Queue;
 
public class Unit extends ICWarsActor implements Interactor, Interactable {
    
    protected Sprite sprite;

    protected String spriteName;
    protected int currentHP;
    protected int maxHP;
    protected int maxDamage;
    protected int radius;
    protected ICWarsRange range;
    protected DiscreteCoordinates coordinates;

    public Unit(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, coordinates, faction);
        sprite = new Sprite(this.getName(), 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.coordinates = coordinates;
        sprite.setDepth(0);









    }
    public void createRange () {

        range = new ICWarsRange();

        int xPosition = this.coordinates.x;
        int yPosition = this.coordinates.y;
        int largeeur = this.getOwnerArea().getWidth();
        int hauteur = this.getOwnerArea().getHeight();
        boolean left,up,right,down;



        for (int fromX = -radius; fromX <= radius; fromX++) {
            for (int fromY = -radius; fromY  <= radius; fromY ++) {

                left = fromX + xPosition > 0;
                right = fromX + xPosition < largeeur - 1;
                up = fromY + yPosition < hauteur - 1;
                down = fromY + yPosition > 0;

                if (xPosition + fromX >= 0 && xPosition + fromX < largeeur && yPosition + fromY  >= 0 && yPosition + fromY  < hauteur) {
                    this.range.addNode(new DiscreteCoordinates(fromX + xPosition, fromY  + yPosition), left, up, right, down);
                }
            }
        }
    }

    public DiscreteCoordinates getCoordinates() {
        return coordinates;
    }

    public int getRadius() {
        return radius;
    }

    public int getDamage() {
    	return maxDamage;
    };

    /**Getter for currentHP
     * @return currentHP
     */
    public int getHP() {
    	return currentHP;
    }
    
    /**Getter for spriteName
     * @return spriteName
     */
    public String getName() {
        return spriteName;
    }
    /**Unit gets damaged by specified amount of HP
     * @param damage
     */
    public void damage(int damage){

        if ((currentHP -= damage) < 0) {
            currentHP=0;
        }else {
            currentHP -= damage;
        }
    }
    /**Unit gets repaired by specified amount of HP
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
    
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {

    }
    
}
