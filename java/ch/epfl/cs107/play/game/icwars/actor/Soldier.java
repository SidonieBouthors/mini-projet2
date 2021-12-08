package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Soldier extends Unit {

    public Soldier(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, coordinates, faction);
        maxHP =5;
        maxDamage =2;
        radius =2;
        this.createRange();

    }
    public String getName() {
            if (ICWarsFaction.ENEMY == this.faction) {
                return "icwars/enemySoldier";
            } else {
                return "icwars/friendlySoldier";
            }
    }


}
