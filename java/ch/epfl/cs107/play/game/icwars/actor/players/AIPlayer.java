package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class AIPlayer extends ICWarsPlayer{
	
	//For waitFor()
	private boolean counting; 
	private float counter;
	//Selecting actions
	private int actionIndex;
	private Action currentAction;
	//List of units that have not yet played this turn
	private List<Unit> toPlayUnits; 
	
	/**
	 * AIPlayer Constructor
	 * @param owner			(Area): area that the AIPlayer belongs to
	 * @param coordinates	(DiscreteCoordinates): starting coordinates
	 * @param faction		(Faction): faction of the AIPlayer
	 * @param units			(Unit[]): array of units belonging to the AIPlayer
	 */
	public AIPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit... units) {
		super(owner, coordinates, faction, units);
		toPlayUnits = new ArrayList<>();
	}

	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		Keyboard keyboard = getOwnerArea().getKeyboard();
		
		//Check for TAB pressed
		if (keyboard.get(Keyboard.TAB).isPressed()) {
				setState(PlayerState.IDLE);
		}
		
		switch (getState()) {
			case IDLE:
				//Reinitialise list of units that have to play
				toPlayUnits.clear();
				toPlayUnits.addAll(getUnits());
				break;
			case NORMAL:
				//If there are still units that need to play, SELECT_CELL
				if (toPlayUnits.size() != 0) {
					setState(PlayerState.SELECT_CELL);
				} 
				//Otherwise the player has finished its turn, IDLE
				else {
					setState(PlayerState.IDLE);
				}
				break;
			case SELECT_CELL:
				//Select the next unit that needs to play and remove it from list
				setSelectedUnit(toPlayUnits.get(0));
				toPlayUnits.remove(getSelectedUnit());
				if (getSelectedUnit() != null && getSelectedUnit().getUsed() == false) {
					setState(PlayerState.MOVE_UNIT);
				} else {
					setState(PlayerState.NORMAL);
				}
				break;
			case MOVE_UNIT:
				//Move player cursor as close as possible to the closest enemy
				moveCloseToEnemy();
				//Wait
				if (waitFor(0.3f, deltaTime)){
					//Move selectedUnit to player cursor
					if (getSelectedUnit().changePosition(this.getCurrentMainCellCoordinates())){
						getSelectedUnit().createRange();
						actionIndex = 0;
						setState(PlayerState.ACTION_SELECTION);
					}
				}
				break;
			case ACTION_SELECTION:
				//Wait
				if (waitFor(0.3f, deltaTime)){
					//Select an action if possible
					if (actionIndex < getSelectedUnit().getActions().size()) {
						currentAction = getSelectedUnit().getActions().get(actionIndex);
						setState(PlayerState.ACTION);
					} 
					//If there are no more possible actions, play next unit
					else {
						setState(PlayerState.SELECT_CELL);
					}
				}
				break;
			case ACTION:
				//Try to execute action
				//If this is not possible, go back to action selection and try next action
				if (!currentAction.doAutoAction(deltaTime, this)) {
					actionIndex++;
					setState(PlayerState.ACTION_SELECTION);
				}
				break;
		}
	}
	
	@Override 
	public void draw(Canvas canvas){
		if (getState() != PlayerState.IDLE) {
	        getSprite().draw(canvas);
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

	/**
	 * Move the player cursor as close as possible to the closest enemy
	 * While staying in range of the current selectedUnit
	 */
	private void moveCloseToEnemy(){
		//get the selectedUnit position, the position of the closest enemy and the radius
		DiscreteCoordinates unitPosition = getSelectedUnit().getCoordinates();
	    DiscreteCoordinates enemyUnitPosition = ((ICWarsArea)getOwnerArea()).getClosestEnemyPosition(getSelectedUnit().getFaction(), unitPosition);
	    int radius = getSelectedUnit().getRadius();
	    int x;
	    int y;
	    
	   	//set x and y to position of target enemy
	    //if there is no target enemy, set x and y to current position
		if (enemyUnitPosition != null) {
			x = enemyUnitPosition.x;
			y = enemyUnitPosition.y;
		}
		else {
			x = unitPosition.x;
			y = unitPosition.y;
		}
		//Adjust x position until it is within radius
	    while( x > unitPosition.x +  radius
        	|| x < unitPosition.x - radius ) {
	    	//increase x if x is too small / decrease x if x is too big
        	if (x < unitPosition.x) { x++; }
        	else if (x > unitPosition.x) { x--; }
	    }
	    //Adjust y position until it is within radius
	    while( y > unitPosition.y + radius
        	|| y < unitPosition.y - radius ) {
	    	//increase y if y is too small / decrease y if y is too big
        	if (y < unitPosition.y) { y++; }
        	else if (y > unitPosition.y) { y--; }
        } 
	    //Adjust position until it is a free cell (not occupied by another unit)
	    //switching between incrementing x and y directions
	    boolean directionSwitch = true;
	    while(((ICWarsArea)getOwnerArea()).isUnitAt(x,y) 
	    		&& (getSelectedUnit().getCoordinates().x !=x 
	    			|| getSelectedUnit().getCoordinates().y !=y)){
	    	if (directionSwitch) {
		    	if (x < unitPosition.x) { x++; } 
		    	else {x--;}
	    	} else {
	    		if (y < unitPosition.y) { y++; }
	    		else {y--;}
	    	}
	    	directionSwitch = !directionSwitch;
	    }
	    //Move player cursor to new position
	    changePosition(new DiscreteCoordinates( x , y ));
	}
}
