package ch.epfl.cs107.play.game.icwars.actor.unit;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Rocket extends Unit {

	/**
     * Default Tank Constructor
     * @param owner			(Area): owner area
     * @param coordinates	(DiscreteCoordinates): starting coordinates
     * @param faction		(Faction): faction
     */
    public Rocket(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates,faction);
        this.setMaxHP(2);
        this.setRadius(1);
        this.setAttackRadius(5);
        this.setMaxDamage(8);
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
            return "icwars/enemyRocket";
        } else if (Faction.ALLY == this.getFaction()){
            return "icwars/friendlyRocket";
        } else {
        	return "icwars/neutralRocket";
        }
    }
    /**
     * Getter for name
     * @return name
     */
    public String getName() {
    	return "Rocket";
    }
}
