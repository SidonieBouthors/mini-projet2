package ch.epfl.cs107.play.game.icwars.actor.unit;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Soldier extends Unit {
	
    public Soldier(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates, faction);
        maxHP =5;
        maxDamage =2;
        radius =2;
        currentHP=maxHP;
        createRange();
        actions = new ArrayList<Action>();
        actions.add(new Attack(this, owner));
        actions.add(new Wait(this, owner));
		
    }
    @Override
    public String getSpriteName() {
            if (Faction.ENEMY == this.faction) {
                return "icwars/enemySoldier";
            } else {
                return "icwars/friendlySoldier";
            }
    }
    public String getName() {
    	return "Soldier";
    }
}
