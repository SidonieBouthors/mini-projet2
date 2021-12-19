package ch.epfl.cs107.play.game.icwars.actor.players;



import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior.ICWarsCell;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class RealPlayer extends ICWarsPlayer{
	
	private ICWarsPlayerGUI gui;
	private final ICWarsPlayerInteractionHandler handler;
	private final static int MOVE_DURATION = 8; //Animation duration in frame number
	private Action currentAction;

	/**
	 * Default RealPlayer Constructor
	 * @param owner			(Area): area that the RealPlayer belongs to
	 * @param coordinates	(DiscreteCoordinates): starting coordinates
	 * @param faction		(Faction): faction of the RealPlayer
	 * @param units			(Unit[]): array of units belonging to the RealPlayer
	 */
	public RealPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit... units) {
		super(owner, coordinates, faction, units);
		
		gui = new ICWarsPlayerGUI(10.f,this);
		handler = new ICWarsPlayerInteractionHandler();
	}
	
	@Override
	public void update(float deltaTime) {

		Keyboard keyboard = getOwnerArea().getKeyboard();

		moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
		moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
		moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
		moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

		super.update(deltaTime);
		
		switch (getState()) {
			case IDLE:
				setSelectedUnit(null);
				gui.setCurrentUnit(null);
				break; 
			case NORMAL:
				if (keyboard.get(Keyboard.ENTER).isPressed()) {
					setState(PlayerState.SELECT_CELL);
				} else if (keyboard.get(Keyboard.TAB).isPressed()) {
					setState(PlayerState.IDLE);
				}
				break;
			case SELECT_CELL:
				if (getSelectedUnit() != null && getSelectedUnit().getUsed() == false) {
					setState(PlayerState.MOVE_UNIT);
				} else {
					setState(PlayerState.NORMAL);
				}
				break;
			case MOVE_UNIT:
				if (keyboard.get(Keyboard.ENTER).isPressed()) {
					if (getSelectedUnit().changePosition(this.getCurrentMainCellCoordinates())){
						getSelectedUnit().createRange();
						setState(PlayerState.ACTION_SELECTION);
					}
				}
				break;
			case ACTION_SELECTION:
				List<Action> actions = getSelectedUnit().getActions();
				for (Action act:actions) {
					if (keyboard.get(act.getKey()).isPressed()) {
						currentAction = act;
						setState(PlayerState.ACTION);
					}
				}
				break;
			case ACTION:
				currentAction.doAction(deltaTime, this, keyboard);
				break;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		//Draw cursor if not IDLE
		PlayerState state = getState();
		if (state != PlayerState.IDLE) {
			getSprite().draw(canvas);
			gui.draw(canvas);
			if (state == PlayerState.ACTION) {
				for (Action action:getSelectedUnit().getActions()) {
		        	action.draw(canvas);
		        }
			}
		}
	}

	/**
	 * Orientate and Move this player in the given orientation if the given button is down
	 * @param orientation (Orientation): given orientation, not null
	 * @param b           (Button): button corresponding to the given orientation, not null
	 */
	private void moveIfPressed(Orientation orientation, Button b) {
		PlayerState state = getState();
		if (state == PlayerState.NORMAL 
			|| state == PlayerState.SELECT_CELL 
			|| state == PlayerState.MOVE_UNIT) {
			if (b.isDown()) {
				if (!isDisplacementOccurs()) {
					gui.setCurrentUnit(null);
					orientate(orientation);
					move(MOVE_DURATION);
				}
			}
		}
	}
	
	@Override
	public void enterArea(Area area, DiscreteCoordinates position) {
		super.enterArea(area, position);
		area.setViewCandidate(this);
		resetMotion();
	}	

	@Override
	public void interactWith(Interactable other) {
		if (!isDisplacementOccurs()) {
			other.acceptInteraction(handler);
	    }
	}

	private class ICWarsPlayerInteractionHandler implements ICWarInteractionVisitor {
		
		@Override
		public void interactWith(Unit unit) {
			gui.setCurrentUnit(unit);
			if (getState() == PlayerState.SELECT_CELL && unit.getFaction() == getFaction()) {
				setSelectedUnit(unit);
				gui.setSelectedUnit(unit);
			}
		}
		@Override
		public void interactWith(Cell cell) {
			gui.setCurrentCellType(((ICWarsCell)cell).getCellType());
		}
		
	}
}


