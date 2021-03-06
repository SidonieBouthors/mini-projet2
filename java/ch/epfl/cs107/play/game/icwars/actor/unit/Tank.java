package ch.epfl.cs107.play.game.icwars.actor.unit;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;


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
        this.setRadius(3);
        this.setAttackRadius(4);
        this.setMaxDamage(7);
        this.setCurrentHPToMax();
        this.createRange();
        //create actions
        List<Action> actions = new ArrayList<Action>();
        actions.add(new Attack(this, owner));
        actions.add(new Wait(this, owner));
        this.setActions(actions);
    }
    // Special Tank Just for Sprite : No actions, range etc.
    public Tank (Area owner, DiscreteCoordinates coordinates, Faction faction, float spriteWidth, float spriteHeigth, Vector vector){
        super(owner, coordinates, faction,spriteWidth,spriteHeigth,vector);
        this.setMaxHP(10);
        this.setCurrentHPToMax();
    }

    
    @Override
    public String getSpriteName() {
        if (Faction.ENEMY == this.getFaction()) {
            return "icwars/enemyTankOrientateLeft";
        } else if (Faction.ALLY == this.getFaction()){
            return "icwars/friendlyTank";
        } else {
            return "icwars/neutralTank";
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
