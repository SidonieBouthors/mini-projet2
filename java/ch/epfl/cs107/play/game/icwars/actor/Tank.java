package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Tank extends Unit {


    public Tank(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, orientation, coordinates, faction);
        maxHp=10;
        radius=4;
        maxDamage=7;
    }


    public String getName() {
        if (ICWarsFaction.ENEMY == this.faction) {
            return "icwars/enemyTank";
        } else {
            return "icwars/friendlyTank";
        }
    }


}
