package ch.epfl.cs107.play.game.icwars.actor.players;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class RealPlayer extends ICWarsPlayer {
	private Sprite sprite;
	private ICWarsPlayerGUI gui;



	/// Animation duration in frame number
    private final static int MOVE_DURATION = 8;
    
	public RealPlayer(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction, Unit... units) {
		super(owner,coordinates,faction, units);
		if (faction == ICWarsFaction.ALLY) {
			sprite = new Sprite("icwars/allyCursor", 1.f, 1.f, this, null, new Vector(0,0));
		}
		else {
			sprite = new Sprite("icwars/enemyCursor", 1.f, 1.f, this, null, new Vector(0, 0));
		}
		
		sprite.setDepth(1);
		centerCamera();
		resetMotion();
		gui = new ICWarsPlayerGUI(10.f,this);

	}
	 
	 @Override

	 public void update(float deltaTime) {

		Keyboard keyboard= getOwnerArea().getKeyboard();
		
		moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
      
        super.update(deltaTime);


		 switch (state) {
			 case IDLE:
				 break;
			 case NORMAL:
				 if (keyboard.get(Keyboard.ENTER).isPressed()) {
					 state=ICWarsPlayerState.SELECT_CELL;
				 } else if (keyboard.get(Keyboard.TAB).isPressed()) {
					 state = ICWarsPlayerState.IDLE;
				 }
			 case SELECT_CELL:
				 if (selectedUnit != null && this.getCurrentMainCellCoordinates()== selectedUnit.getCoordinates()) {
					 state = ICWarsPlayerState.MOVE_UNIT;

				 }
			 case MOVE_UNIT:
				 if (state == ICWarsPlayerState.MOVE_UNIT && keyboard.get(Keyboard.ENTER).isPressed()) {
					 selectedUnit.changePosition(this.getCurrentMainCellCoordinates());
					 selectedUnit.setUsed(true);
					 state = ICWarsPlayerState.NORMAL;
					 break;
				 }
			 case ACTION:
			 case ACTION_SELECTION:
	     }
	       
	}

	 /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param b (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {
		if (state == ICWarsPlayerState.NORMAL || state == ICWarsPlayerState.SELECT_CELL || state == ICWarsPlayerState.MOVE_UNIT){
			if (b.isDown()) {
				if (!isDisplacementOccurs()) {
					orientate(orientation);
					move(MOVE_DURATION);
				}
			}
		}
	}


    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    @Override
    public void enterArea(Area area, DiscreteCoordinates position){
		super.enterArea(area, position);        
		area.setViewCandidate(this);
        resetMotion();
    }
    
	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		gui.draw(canvas);
	}

	/*public void selectUnit(int i) {
		if (i < units.size()) {
			selectedUnit = units.get(i);
		}
	}*/
	private class ICWarsPlayerInteractionHandler implements ICWarInteractionVisitor {
		@Override
		public void interactWith(Unit unit) {
			if (state == ICWarsPlayerState.SELECT_CELL && unit.getFaction() == faction) {
				selectedUnit = unit;
			}
		}
	}

}
