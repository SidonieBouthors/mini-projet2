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

public class Soldier extends Unit {

    public Soldier(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, coordinates, faction);
        maxHP =5;
        maxDamage =2;
        radius =2;


    }
    public String getName() {
            if (ICWarsFaction.ENEMY == this.faction) {
                return "icwars/enemySoldier";
            } else {
                return "icwars/friendlySoldier";
            }
    }


}
