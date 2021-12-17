package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer.ICWarsPlayerInteractionHandler;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;

public class AIPlayer extends ICWarsPlayer{

	private Sprite sprite;
	boolean counting;
	float counter;
	private Action currentAction;
	private final static int MOVE_DURATION = 8;
	
	
	public AIPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit[] units) {
		super(owner, coordinates, faction, units);
			if (faction == Faction.ALLY) {
				sprite = new Sprite("icwars/allyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
			} else {
				sprite = new Sprite("icwars/enemyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
			}
		sprite.setDepth(1);
	}

	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		deleteDeadunits();
		
		Keyboard keyboard = getOwnerArea().getKeyboard();
		
		switch (state) {
			case IDLE:
				break;
			case NORMAL:
				if (keyboard.get(Keyboard.TAB).isPressed()) {
					this.state = PlayerState.IDLE;
				}
				//if reste des unit a bouger?
				this.state = PlayerState.SELECT_CELL;
				//else next joueur
				break;
			case SELECT_CELL:
				if (selectedUnit != null && selectedUnit.getUsed() == false) {
					state = PlayerState.MOVE_UNIT;
				} else {
					state = PlayerState.NORMAL;
				}
				break;
			case MOVE_UNIT:
				//decider ou bouger + mettre le curseur la bas
				if (selectedUnit.changePosition(this.getCurrentMainCellCoordinates())){
				selectedUnit.setUsed(true);
				selectedUnit.createRange();
				state = PlayerState.ACTION_SELECTION;}

				break;
			case ACTION_SELECTION:
				List<Action> actions = selectedUnit.getActions();
				for (Action act:actions) {
					//if we can attack, attack. Else wait
					currentAction = act;
					state = PlayerState.ACTION;
					
				}
				break;
			case ACTION:
				currentAction.doAutoAction(deltaTime, this);
				break;
		}
	}
	
	/**
	* Ensures that value time elapsed before returning true
	* @param dt elapsed time
	* @param value waiting time (in seconds)
	* @return true if value seconds has elapsed , false otherwise
	*/
	private boolean waitFor(float value , float dt) {
	if (counting) {
		counter += dt;
		if (counter > value) {
			counting = false;
			return true;
		}
	} else {
		counter = 0f;
		counting = true;
	}
	return false;
	}
}
