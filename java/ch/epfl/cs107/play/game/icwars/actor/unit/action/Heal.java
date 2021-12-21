package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Heal extends Action{

	private ImageGraphics cursor;
	private List<Integer> healableUnitIndexes;
	private int targetNumber = 0;
	
	/**
	 * Attack Constructor
	 * @param unit	(Unit): attacking unit
	 * @param area	(Area): area on which attack occurs
	 */
	public Heal(Unit unit, Area area) {
		super(unit, area);
		this.setName("(H)eal");
		this.setKey(Keyboard.H);
		this.cursor=new ImageGraphics (ResourcePath.getSprite ("icwars/UIpackSheet"),1f, 1f, new RegionOfInterest(4*18 , 26*18 ,16 ,16));
		this.healableUnitIndexes = new ArrayList<Integer>();
		cursor.setDepth(1);
	}
	
	@Override
	public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
		//call ICWarsArea method to return indexes of attackable units
		healableUnitIndexes = ((ICWarsArea)getArea()).getHealable(getUnit().getCoordinates(), getUnit().getAttackRadius(), getUnit().getFaction());
		
		//check if targetNumber is in bounds
				if (targetNumber >= healableUnitIndexes.size()) {
					targetNumber = 0;
		}
				
		//TAB key pressed or no attackable units
		if (healableUnitIndexes.size()==0 
			|| keyboard.get(Keyboard.TAB).isPressed()) {
			player.centerCamera();
			player.setState(PlayerState.ACTION_SELECTION);
		}
		
		//RIGHT key pressed
		if(keyboard.get(Keyboard.RIGHT).isPressed()) {
			if(targetNumber < healableUnitIndexes.size()-1) {
				targetNumber++;
			}
			else {
				targetNumber = 0;
			}
		}
		//LEFT key pressed
		if(keyboard.get(Keyboard.LEFT).isPressed()) {
			if(targetNumber > 0) {
				targetNumber--;
			}
			else {
				targetNumber = healableUnitIndexes.size() - 1;
			}
		}
		//ENTER key pressed
		if(keyboard.get(Keyboard.ENTER).isPressed()) {
			//select target unit and attack it
			int targetUnitIndex = healableUnitIndexes.get(targetNumber);
			((ICWarsArea)getArea()).healUnit(targetUnitIndex, getUnit().getHealing());
			
			getUnit().setUsed(true);
			player.centerCamera();
			player.setState(PlayerState.NORMAL);
		}	
	}
	
	@Override
	public boolean doAutoAction(float dt, ICWarsPlayer player) {
		//call ICWarsArea method to return indexes of attackable units
		healableUnitIndexes = ((ICWarsArea)getArea()).getHealable(getUnit().getCoordinates(), getUnit().getAttackRadius(), getUnit().getFaction());	
		
		//if there are no attackable units
		if (healableUnitIndexes.size()==0) {
			player.centerCamera();
			player.setState(PlayerState.ACTION_SELECTION);
			return false;
		}
		else {
			//select target unit and heal it
			int targetUnitIndex = healableUnitIndexes.get(0);
			((ICWarsArea)getArea()).healUnit(targetUnitIndex, getUnit().getHealing());
			getUnit().setUsed(true);
			player.centerCamera();
			player.setState(PlayerState.NORMAL);
			return true;
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (healableUnitIndexes.size() != 0) {
			int targetUnitIndex = healableUnitIndexes.get(targetNumber);
			((ICWarsArea)getArea()).centerCameraOnUnit(targetUnitIndex);
		}
		cursor.setAnchor (canvas.getPosition ().add (1 ,0));
		cursor.draw(canvas);
	}
}