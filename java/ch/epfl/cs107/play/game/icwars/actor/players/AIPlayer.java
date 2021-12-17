package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.directory.NoSuchAttributeException;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class AIPlayer extends ICWarsPlayer{

	private Sprite sprite;
	boolean counting;
	float counter;
	private Action currentAction;
	private int actionIndex;
	private final static int MOVE_DURATION = 8;
	private List<Unit> toPlayUnits;
	private List<DiscreteCoordinates> enemyUnitsCoordinates;
	
	public AIPlayer(Area owner, DiscreteCoordinates coordinates, Faction faction, Unit... units) {
		super(owner, coordinates, faction, units);
			if (faction == Faction.ALLY) {
				sprite = new Sprite("icwars/allyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
			} else {
				sprite = new Sprite("icwars/enemyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
			}
		sprite.setDepth(1);
		toPlayUnits = new ArrayList<Unit>();
	}

	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		deleteDeadunits();
		
		Keyboard keyboard = getOwnerArea().getKeyboard();
		
		if (keyboard.get(Keyboard.TAB).isPressed()) {
					this.state = PlayerState.IDLE;
		}
		
		switch (state) {
			case IDLE:
				toPlayUnits.addAll(units);
				break;
			case NORMAL:
				
				if (toPlayUnits.size() != 0) {
					this.state = PlayerState.SELECT_CELL;
				} else {
					this.state = PlayerState.IDLE;
				}
				break;
			case SELECT_CELL:
				selectedUnit = toPlayUnits.get(0);
				toPlayUnits.remove(selectedUnit);
				if (selectedUnit != null && selectedUnit.getUsed() == false) {
					state = PlayerState.MOVE_UNIT;
				} else {
					state = PlayerState.NORMAL;
				}
				break;
			case MOVE_UNIT:
				moveCloseToEnemy();
				if (waitFor(0.2f, deltaTime)){
					if (selectedUnit.changePosition(this.getCurrentMainCellCoordinates())){
						selectedUnit.createRange();
						actionIndex = 0;
						state = PlayerState.ACTION_SELECTION;
					}
				}
				break;
			case ACTION_SELECTION:
				if (waitFor(0.2f, deltaTime)){
					if (actionIndex < selectedUnit.getActions().size()) {
						currentAction = selectedUnit.getActions().get(actionIndex);
						state = PlayerState.ACTION;
					} else {
						state = PlayerState.SELECT_CELL;
					}
				}
				break;
			case ACTION:
				if (!currentAction.doAutoAction(deltaTime, this)) {
					actionIndex++;
					state = PlayerState.ACTION_SELECTION;
				}
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

	@Override 
	public void draw(Canvas canvas){
		if (state != PlayerState.IDLE) {
	        sprite.draw(canvas);
        }
	}
	private void moveCloseToEnemy(){
		DiscreteCoordinates unitPosition = selectedUnit.getCoordinates();
	    DiscreteCoordinates enemyUnitPosition = ((ICWarsArea)getOwnerArea()).getClosestEnemyPosition(selectedUnit.getFaction(), unitPosition);
	    
	    int x=0;
	    int y=0;
	    int radius = selectedUnit.getRadius();

	   	//set x to x position of target enemy
		if (enemyUnitPosition != null) {
			x = enemyUnitPosition.x;
			y = enemyUnitPosition.y;
		}


	    //while x not within radius
	    while( x > unitPosition.x +  radius
        	|| x < unitPosition.x - radius ) {
	    	//increase x if x is too small
        	if (x < unitPosition.x) {
        		x += 1;
        	}
        	//decrease x if x is too big
        	else if (x > unitPosition.x) {
        		x -= 1;
        	}
	    }
	    
	    //set y to y position of target enemy

	    //while y not within radius
	    while( y > unitPosition.y + radius
        	|| y < unitPosition.y - radius ) {
	    	//increase y if y is too small
        	if (y < unitPosition.y) {
        		y += 1;
        	}
        	//decrease y if y is too big
        	else if (y > unitPosition.y) {
        		y -= 1;
        	}
        } 
	    while(((ICWarsArea)getOwnerArea()).unitAt(x,y) 
	    		&& (selectedUnit.getCoordinates().x !=x 
	    		|| selectedUnit.getCoordinates().y !=y)){
	    	if (x < unitPosition.x) {
        		x += 1;
        	} else {
        		x -= 1;
        	}
	    }
	    changePosition(new DiscreteCoordinates( x , y ));
	}

	

}
