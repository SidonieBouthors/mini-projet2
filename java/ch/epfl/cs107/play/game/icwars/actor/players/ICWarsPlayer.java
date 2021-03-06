    package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public abstract class ICWarsPlayer extends ICWarsActor implements Interactor {

	private Sprite sprite;
	private List<Unit> units;
	private PlayerState state;
	private Unit selectedUnit;
	
	/**
	 * Default ICWarsPlayer Constructor
	 * @param owner			(Area): area that the ICWarsPlayer belongs to
	 * @param coordinates	(DiscreteCoordinates): starting coordinates
	 * @param faction		(Faction): faction of the ICWarsPlayer
	 * @param units			(Unit[]): array of units belonging to the ICWarsPlayer
	 */
	public ICWarsPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit... units) {
		super(owner, coordinates, faction);

		if (faction == Faction.ALLY) {
			sprite = new Sprite("icwars/allyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
		} else if (faction == Faction.ENEMY){
			sprite = new Sprite("icwars/enemyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
		} else {
			sprite = new Sprite("icwars/neutralCursor", 1.f, 1.f, this, null, new Vector(0, 0));
		}

		sprite.setDepth(1);
		this.units = new ArrayList<>(Arrays.asList(units));
		this.state = PlayerState.IDLE;
	}
	
	@Override
	public void update(float deltaTime){
	    super.update(deltaTime);
        if(!this.isDisplacementOccurs()){
            deleteDeadUnits();
	    }
	}
	
	/**
	 * Enum of all possible Player states
	 */
	public enum PlayerState {
		IDLE,
		NORMAL,
		SELECT_CELL,
		MOVE_UNIT,
		ACTION_SELECTION,
		ACTION;
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
		return units.size() == 0;
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
			}
		}
	}
	
	//****Getters and Setters****
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
	public PlayerState getState() { return this.state; }
	/**
	 * Setter for player state
	 * @param state	(PlayerState)
	 */
	public void setState(PlayerState state) { this.state=state;	}
	/**
	 * Getter for sprite (Protected)
	 * @return sprite	(Sprite)
	 */
	protected Sprite getSprite() {return this.sprite;}
	/**
	 * Getter for units (Protected)
	 * @return	units	(List<Unit>): units
	 */
	protected List<Unit> getUnits() {return Collections.unmodifiableList(units);}
	/**
	 * Getter for selectedUnit (Protected)
	 * @return	unit	(Unit): selectedUnit
	 */
	protected Unit getSelectedUnit() {return this.selectedUnit;}
	/**
	 * Setter for selectedUnit (Protected)
	 * @param unit	(Unit) selectedUnit
	 */
	protected void setSelectedUnit(Unit unit) {this.selectedUnit = unit;}

	
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
