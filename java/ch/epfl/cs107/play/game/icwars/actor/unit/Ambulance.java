package ch.epfl.cs107.play.game.icwars.actor.unit;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Heal;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Ambulance extends Unit {

	/**
     * Default Tank Constructor
     * @param owner			(Area): owner area
     * @param coordinates	(DiscreteCoordinates): starting coordinates
     * @param faction		(Faction): faction
     */
    public Ambulance(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates,faction);
        this.setMaxHP(10);
        this.setRadius(3);
        this.setAttackRadius(4); //used as healRadius here
        this.setMaxDamage(1);
        this.setMaxHealing(3);
        this.setCurrentHPToMax();
        this.createRange();
        //create actions
        List<Action> actions = new ArrayList<Action>();
        actions.add(new Heal(this, owner));
        actions.add(new Wait(this, owner));
        this.setActions(actions);
    }

    
    @Override
    public String getSpriteName() {
        if (Faction.ENEMY == this.getFaction()) {
            return "icwars/enemyAmbulance";
        } else if (Faction.ALLY == this.getFaction()){
            return "icwars/friendlyAmbulance";
        } else {
        	return "icwars/neutralAmbulance";
        }
    }
    /**
     * Getter for name
     * @return name
     */
    public String getName() {
    	return "Healer";
    }
}
