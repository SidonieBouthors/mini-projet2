package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;

public class ICWarsPlayer extends ICWarsActor{

	protected ArrayList<Unit> units;
	private ICWarsPlayerGUI gui;
	protected ICWarsPlayerState state;


	public enum ICWarsPlayerState {
		IDLE(0),
		NORMAl(1),
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
		Keyboard keyboard = getOwnerArea().getKeyboard();



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

	@Override
	public boolean takeCellSpace() {
		return true;
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
