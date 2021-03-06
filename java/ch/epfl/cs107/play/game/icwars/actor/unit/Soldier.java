package ch.epfl.cs107.play.game.icwars.actor.unit;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Soldier extends Unit {
	
	/**
     * Default Soldier Constructor
     * @param owner			(Area): owner area
     * @param coordinates	(DiscreteCoordinates): starting coordinates
     * @param faction		(Faction): faction
     */
    public Soldier(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates, faction);
        this.setMaxHP(5);
        this.setMaxDamage(2);
        this.setRadius(2);
        this.setAttackRadius(2);
        this.setCurrentHPToMax();
        this.createRange();
        //create actions
        List<Action> actions = new ArrayList<Action>();
        actions.add(new Attack(this, owner));
        actions.add(new Wait(this, owner));
        this.setActions(actions);
        
		
    }
    @Override
    public String getSpriteName() {
        if (Faction.ENEMY == this.getFaction()) {
            return "icwars/enemySoldier";
        } else if (Faction.ALLY == this.getFaction()) {
            return "icwars/friendlySoldier";
        } else {
            return "icwars/neutralSoldier";
        }
    }
    /**
     * Getter for name
     * @return name
     */
    public String getName() {
    	return "Soldier";
    }
}
