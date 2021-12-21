package ch.epfl.cs107.play.game.icwars.Menu;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class MenuTank extends ICWarsActor {
    private Sprite sprite;

    /**
     * Default ICWarsActor Constructor
     *
     * @param owner       (Area): owner area
     * @param coordinates (DiscreteCoordinates): starting coordinates
     * @param faction     (Faction): faction of actor
     */
    public MenuTank(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates, faction);
        sprite = new Sprite("icwars/enemyTank",4.f,4.f,this,null,new Vector(0,-1));
    }


    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
    @Override
    public boolean takeCellSpace() {return true;}



}
