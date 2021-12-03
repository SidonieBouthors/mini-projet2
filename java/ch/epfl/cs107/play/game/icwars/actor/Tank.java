package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Tank extends Unit {
    public final int MAX_HP_TANK = 10;
    public final int DAMAGE_TANK = 7;

    public Tank(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, orientation, coordinates, faction);
    }


    public int getDamage() {
        return DAMAGE_TANK;
    }


}
