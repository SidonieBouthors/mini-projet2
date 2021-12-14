package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ICWarsPlayer extends ICWarsActor implements Interactor {

	protected ArrayList<Unit> units;
	private ICWarsPlayerGUI gui;
	protected PlayerState state;
	protected Unit selectedUnit;
	protected DiscreteCoordinates coordinates;
	
	public ICWarsPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit... units) {
		super(owner, coordinates, faction);
		this.units = new ArrayList<Unit>(Arrays.asList(units));
		gui = new ICWarsPlayerGUI(10.f,this);
		state = PlayerState.IDLE;
		this.coordinates=coordinates;
		this.faction=faction;
	}
	@Override
	public void update(float deltaTime){
	    super.update(deltaTime);
        if(this.isDisplacementOccurs()){
            for(int i =0; i < units.size();++i){
                if( units.get(i).getHP() ==0 ){
                    units.remove(i);
                }
            }
	    }
	}
	
	public void startTurn() {
		this.state = PlayerState.NORMAL;
		centerCamera();
		for (Unit unit:units) {
			unit.setUsed(false);
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
	 * Getter for state of Player
	 * Non intrusive getter : An object ICWarsPlayerState is immutable.
	 * @return state
	 */
	public PlayerState getState() {
		return state;
	}
	/**
	 * Getter for current cell occupied by Player
	 * Non intrusive getter : An object Discrete Coordinates is immutable.
	 * @return currentMainCellCoordinates
	 */
	public DiscreteCoordinates getCoordinates() {
		return getCurrentMainCellCoordinates();
	}
	/**
	 * Centre the camera on the player
     */
	public void centerCamera() {
		getOwnerArea().setViewCandidate(this);
	}
	/**
	 * Checks if player has been defeated
	 * @return whether the player is defeated
	 */
	public boolean isDefeated() {
		if(units.size() == 0) {
			return true;
		} else { return false;}
	}
	/**
	 * Set all unit.used to false (all units not used)
	 */
	public void setUnitsUsable() {
		for (Unit unit : units) {
			unit.setUsed(false);
		}
	}
	/**
	 * Setter for player state
	 * @param state
	 */
	public void setState(PlayerState state) {
		this.state=state;
		
	}
	
	@Override
	public void onLeaving(List<DiscreteCoordinates> coordinates) {
		if (state == PlayerState.SELECT_CELL) {
			state = PlayerState.NORMAL;
		}
	}
	
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
	public List<DiscreteCoordinates> getFieldOfViewCells() {return null;}

	@Override
	public boolean wantsCellInteraction() {return true;}
	
	@Override
	public boolean wantsViewInteraction() {return false;}

	@Override
	public void interactWith(Interactable other) {}
	
	@Override
    public boolean isCellInteractable() {return true;}
	
	@Override
	public boolean isViewInteractable() {return false;}

}
