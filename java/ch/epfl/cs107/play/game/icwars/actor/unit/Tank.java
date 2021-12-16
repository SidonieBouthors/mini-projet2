package ch.epfl.cs107.play.game.icwars.actor.unit;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Tank extends Unit {

    public Tank(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates,faction);
        maxHP =10;
        radius=4;
        maxDamage=7;
        currentHP = maxHP;
        actions = new ArrayList<Action>();
        actions.add(new Wait(this, owner));
		actions.add(new Attack(this, owner));
        this.createRange();
    }
    public DiscreteCoordinates getSpawnPosition() {
        if (this.faction == Faction.ALLY) {
            return new DiscreteCoordinates(2,5);
        } else { return new DiscreteCoordinates(4,5); }
    }
    @Override
    public String getSpriteName() {
        if (Faction.ENEMY == this.faction) {
            return "icwars/enemyTank";
        } else {
            return "icwars/friendlyTank";
        }
    }
    public String getName() {
    	return "Tank";
    }
}
