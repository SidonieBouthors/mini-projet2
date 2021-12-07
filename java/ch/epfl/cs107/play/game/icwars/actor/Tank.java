package ch.epfl.cs107.play.game.icwars.actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Tank extends Unit {



    public Tank(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, coordinates,faction);
        maxHP =10;
        radius=4;
        maxDamage=7;
        this.createRange();


    }




    public DiscreteCoordinates getSpawnPosition() {
        if (this.faction == ICWarsFaction.ALLY) {
            return new DiscreteCoordinates(2,5);
        } else { return new DiscreteCoordinates(4,5); }
    }
    public String getName() {
        if (ICWarsFaction.ENEMY == this.faction) {
            return "icwars/enemyTank";
        } else {
            return "icwars/friendlyTank";
        }
    }



}
