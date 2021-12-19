package ch.epfl.cs107.play.game.icwars.actor.unit;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Tank extends Unit {

	/**
     * Default Tank Constructor
     * @param owner			(Area): owner area
     * @param coordinates	(DiscreteCoordinates): starting coordinates
     * @param faction		(Faction): faction
     */
    public Tank(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner, coordinates,faction);
        this.setMaxHP(10);
        this.setRadius(4);
        this.setMaxDamage(7);
        this.setCurrentHPToMax();
        this.createRange();
        //create actions
        List<Action> actions = new ArrayList<Action>();
        actions.add(new Attack(this, owner));
        actions.add(new Wait(this, owner));
        this.setActions(actions);
    }
    
    public DiscreteCoordinates getSpawnPosition() {
        if (this.getFaction() == Faction.ALLY) {
            return new DiscreteCoordinates(2,5);
        } else { return new DiscreteCoordinates(4,5); }
    }
    
    @Override
    public String getSpriteName() {
        if (Faction.ENEMY == this.getFaction()) {
            return "icwars/enemyTank";
        } else {
            return "icwars/friendlyTank";
        }
    }
    /**
     * Getter for name
     * @return name
     */
    public String getName() {
    	return "Tank";
    }
}
