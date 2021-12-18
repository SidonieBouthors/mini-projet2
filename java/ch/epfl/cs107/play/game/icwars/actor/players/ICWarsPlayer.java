package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ICWarsPlayer extends ICWarsActor implements Interactor {

	protected List<Unit> units;
	protected List<Unit> deadUnits;
	protected List<Unit> toRemove; //Special list to avoid modification while iterating on units.
	protected PlayerState state;
	protected Unit selectedUnit;
	
	public ICWarsPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit... units) {
		super(owner, coordinates, faction);
		this.units = new ArrayList<Unit>(Arrays.asList(units));
		this.deadUnits = new ArrayList<Unit>();
		this.state = PlayerState.IDLE;
	}
	
	@Override
	public void update(float deltaTime){
	    super.update(deltaTime);
        if(this.isDisplacementOccurs()){
            for(int i =0; i < units.size();++i){
                if( units.get(i).getHP() ==0 ){ units.remove(i); }
            }
	    }
	}
	
	/**
	 * Enum of all possible Player states
	 */
	public enum PlayerState {
		IDLE(0),
		NORMAL(1),
		SELECT_CELL(2),
		MOVE_UNIT(3),
		ACTION_SELECTION(4),
		ACTION(5);
		private int state;
		
		PlayerState(int state) {
			this.state=state;
		}
	}
	
	/**
	 * Starts player turn
	 */
	public void startTurn() {
		this.state = PlayerState.NORMAL;
		centerCamera();
		setUnitsUsable();
	}
	/**
	 * Centre the camera on the player
     */
	public void centerCamera() {
		getOwnerArea().setViewCandidate(this);
	}
	/**
	 * Checks if player has been defeated
	 * @return defeated	(boolean): whether the player is defeated
	 */
	public boolean isDefeated() {
		if(units.size() == 0) { return true; } 
		else { return false; }
	}
	/**
	 * Set all unit used to false (make all units not used)
	 */
	public void setUnitsUsable() {
		for (Unit unit : units) { unit.setUsed(false); }
	}
	/**
	 * Iterates over list of units and remove any unit who has no more HP
	 */
	protected void deleteDeadUnits() {
			for (Iterator<Unit> iterator = units.iterator(); iterator.hasNext();) {
				Unit unit = iterator.next();
				if(unit.getHP() == 0) {
					((ICWarsArea)getOwnerArea()).removeUnit(unit);
					iterator.remove();
					deadUnits.add(unit);
				}
			}
			// Il faut update la liste des acteurs qui sont selectionnable par le curseur pour en enlever les morts
	}
	/**
	 * Getter for coordinates of player
	 * - Non intrusive getter : DiscreteCoordinates is immutable.
	 * @return coordinates	(DiscreteCoordinates): current coordinates of player
	 */
	public DiscreteCoordinates getCoordinates(){ return getCurrentCells().get(0); }
	/**
	 * Getter for state of Player
	 * - Non intrusive getter : PlayerState is immutable.
	 * @return state	(PlayerState): current state of player
	 */
	public PlayerState getState() { return state; }
	/**
	 * Setter for player state
	 * @param state	(PlayerState)
	 */
	public void setState(PlayerState state) { this.state=state;	}
	
	@Override
    public void enterArea(Area area, DiscreteCoordinates position){
		super.enterArea(area,position);
    	for(Unit unit :units){
			//Calling the method from ICWarsActor to register and set OwnerArea to the units
			//unit.enterArea(area,unit.getCoordinates());
    		((ICWarsArea)area).addUnit(unit);
        }
    }
	
    @Override
    public void leaveArea(){
    	Area area = getOwnerArea();
        for (Unit unit:units) {
            area.unregisterActor(unit);
        }
        area.unregisterActor(this);
    }
    
    @Override
	public void onLeaving(List<DiscreteCoordinates> coordinates) {
		if (state == PlayerState.SELECT_CELL) {
			state = PlayerState.NORMAL;
		}
	}	
    
    @Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {return null;}

	@Override
	public boolean wantsCellInteraction() {return true;}
	
	@Override
	public boolean wantsViewInteraction() {return false;}

	@Override
	public void interactWith(Interactable other) {}

}
