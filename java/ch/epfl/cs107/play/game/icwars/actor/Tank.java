package ch.epfl.cs107.play.game.icwars.actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Tank extends Unit {

    public Tank(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates,faction);
        maxHP =10;
        radius=4;
        maxDamage=7;
        currentHP = maxHP;
        this.createRange();
    }
    public DiscreteCoordinates getSpawnPosition() {
        if (this.faction == Faction.ALLY) {
            return new DiscreteCoordinates(2,5);
        } else { return new DiscreteCoordinates(4,5); }
    }
    public String getName() {
        if (Faction.ENEMY == this.faction) {
            return "icwars/enemyTank";
        } else {
            return "icwars/friendlyTank";
        }
    }
}
