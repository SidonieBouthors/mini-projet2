package ch.epfl.cs107.play.game.icwars.actor.players;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;

import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class RealPlayer extends ICWarsPlayer{
	
	private Sprite sprite;
	private ICWarsPlayerGUI gui;
	private final ICWarsPlayerInteractionHandler handler;
	/// Animation duration in frame number
	private final static int MOVE_DURATION = 8;

	public RealPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit... units) {
		super(owner, coordinates, faction, units);
		if (faction == Faction.ALLY) {
			sprite = new Sprite("icwars/allyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
		} else {
			sprite = new Sprite("icwars/enemyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
		}
		gui = new ICWarsPlayerGUI(10.f,this);
		sprite.setDepth(1);
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

		switch (state) {
			case IDLE:
				break;
			case NORMAL:
				if (keyboard.get(Keyboard.ENTER).isReleased()) {
					this.state = PlayerState.SELECT_CELL;
				} else if (keyboard.get(Keyboard.TAB).isPressed()) {
					this.state = PlayerState.IDLE;
				}
				break;
			case SELECT_CELL:
				if (selectedUnit != null && selectedUnit.getUsed() == false) {
					state = PlayerState.MOVE_UNIT;
				} else {
					state = PlayerState.NORMAL;
				}
				break;
			case MOVE_UNIT:
				if (keyboard.get(Keyboard.ENTER).isReleased()) {
					if (selectedUnit.changePosition(this.getCurrentMainCellCoordinates())){
					selectedUnit.changePosition(this.getCurrentMainCellCoordinates());
					selectedUnit.setUsed(true);
					selectedUnit.createRange();
					state = PlayerState.NORMAL;}
				}
			case ACTION:
				break;
			case ACTION_SELECTION:
				break;
		}
	}

	/**
	 * Orientate and Move this player in the given orientation if the given button is down
	 *
	 * @param orientation (Orientation): given orientation, not null
	 * @param b           (Button): button corresponding to the given orientation, not null
	 */
	private void moveIfPressed(Orientation orientation, Button b) {
		if (state == PlayerState.NORMAL || state == PlayerState.SELECT_CELL || state == PlayerState.MOVE_UNIT) {
			if (b.isDown()) {
				if (!isDisplacementOccurs()) {
					orientate(orientation);
					move(MOVE_DURATION);
				}
			}
		}
	}
	
	/**
	 * Calls the setSelectedUnit setter of GUI so they access the selected unit
	 */
	private void setSelectedUnitToGui() {
		gui.setSelectedUnit(selectedUnit);
	}
	
	@Override
	public void enterArea(Area area, DiscreteCoordinates position) {
		super.enterArea(area, position);
		area.setViewCandidate(this);
		resetMotion();
	}

	@Override
	public void draw(Canvas canvas) {
		// Si le STATE est IDLE alors ne pas draw le cursor
		if (state != PlayerState.IDLE) {
			sprite.draw(canvas);
			gui.draw(canvas);
		}
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
			if (state == PlayerState.SELECT_CELL && unit.getFaction() == faction) {
				selectedUnit = unit;
				setSelectedUnitToGui();
			}
		}
		@Override
		public void interactWith(RealPlayer player) {

		}
	}
	
	/*public void selectUnit(int i) {
		if (i < units.size()) {
			selectedUnit = units.get(i);
		}
	}*/
}


