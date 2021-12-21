package ch.epfl.cs107.play.game.icwars.Menu;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class MenuText extends ICWarsActor {
    private Sprite sprite;

    /**
     * Default ICWarsActor Constructor
     *
     * @param owner       (Area): owner area
     * @param coordinates (DiscreteCoordinates): starting coordinates
     * @param faction     (Faction): faction of actor
     */
    public MenuText(Area owner, DiscreteCoordinates coordinates, String spriteName, float width, float height) {
        super(owner, coordinates, Faction.NONE);
        sprite = new Sprite(spriteName,width,height,this,null,new Vector(0,0));
    }
    
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
    @Override
    public boolean takeCellSpace() {return true;}
}