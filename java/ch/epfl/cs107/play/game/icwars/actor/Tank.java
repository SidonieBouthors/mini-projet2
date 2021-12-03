package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Tank extends Unit {

    public Tank(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, coordinates,faction);
        maxHP =10;
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
