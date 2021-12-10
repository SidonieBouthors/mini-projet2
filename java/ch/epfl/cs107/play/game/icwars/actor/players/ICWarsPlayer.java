package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ICWarsPlayer extends ICWarsActor implements Interactor {

	protected ArrayList<Unit> units;
	private ICWarsPlayerGUI gui;
	protected ICWarsPlayerState state;
	protected Unit selectedUnit;
	protected DiscreteCoordinates coordinates;


	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return null;
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return false;
	}

	@Override
	public void interactWith(Interactable other) {

	}


	public enum ICWarsPlayerState {
		IDLE(0),
		NORMAL(1),
		SELECT_CELL(2),
		MOVE_UNIT(3),
		ACTION_SELECTION(4),
		ACTION(5);
		private int state;

		ICWarsPlayerState(int state) {
			this.state=state;
		}

	}

	public ICWarsPlayer(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction, Unit... units) {
		super(owner, coordinates, faction);
		this.units = new ArrayList<Unit>(Arrays.asList(units));
		gui = new ICWarsPlayerGUI(10.f,this);
		state = ICWarsPlayerState.IDLE;
		this.coordinates=coordinates;
		this.faction=faction;

	}

	// Non intrusive getter : An object ICWarsPlayerState is immutable.
	public ICWarsPlayerState getState() {
		return state;
	}

	// Non intrusive getter : An object Unit is immutable.
	public Unit getSelectedUnit() {return selectedUnit;}

	// Non intrusive getter : An object Discrete Coordinates is immutable.
	public DiscreteCoordinates getCoordinates() {
		return coordinates;
	}


	/**Centres the camera on the player
     */
	public void centerCamera() {
		getOwnerArea().setViewCandidate(this);
	}
	/**Checks if player has been defeated
	 * @return whether the player is defeated
	 */
	public boolean isDefeated() {
		if(units.size() == 0) {
			return true;
		}
		else { return false;}
	}
	public void startTurn() {
		state = ICWarsPlayerState.NORMAL;
		centerCamera();
		for (Unit unit:units) {
			unit.setUsed(false);
		}
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


	@Override
	public void onLeaving(List<DiscreteCoordinates> coordinates) {
		if (state == ICWarsPlayerState.SELECT_CELL) {
			state = ICWarsPlayerState.NORMAL;
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
    public void enterArea(Area area, DiscreteCoordinates position){
		super.enterArea(area,position);
    	for(Unit unit :units){
			//Calling the method from ICWarsActor to register and set OwnerArea to the units
			unit.enterArea(area,unit.getCoordinates());
        }
    }


	public boolean takeCellSpace() {
		return false;
	}
	@Override
    public boolean isCellInteractable() {
        return true;
    }
	@Override
	public boolean isViewInteractable() {
		return false;
	}
	
}
