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

public class Attack extends Action{

	private ImageGraphics cursor;
	private List<Integer> attackableUnitIndexes;
	int targetNumber=0;
	
	public Attack(Unit unit, Area area) {
		super(unit, area);
		this.name = "(A)ttack";
		this.key = Keyboard.A;
		this.cursor=new ImageGraphics (ResourcePath.getSprite ("icwars/UIpackSheet"),1f, 1f, new RegionOfInterest(4*18 , 26*18 ,16 ,16));
		this.attackableUnitIndexes = new ArrayList<Integer>();
	}
	
	@Override
	public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
		//call ICWarsArea method to return indexes of attackable units
		attackableUnitIndexes = ((ICWarsArea)area).getAttackable(unit.getPosition(), unit.getRadius(), unit.getFaction());
		
		if (attackableUnitIndexes.size()==0 || keyboard.get(Keyboard.TAB).isPressed()) {
			player.centerCamera();
			player.setState(PlayerState.ACTION_SELECTION);
		}
		
		//RIGHT key pressed
		if(keyboard.get(Keyboard.RIGHT).isPressed()) {
			if(targetNumber < attackableUnitIndexes.size()-1) {
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
				targetNumber = attackableUnitIndexes.size() - 1;
			}
		}
		//ENTER key pressed
		if(keyboard.get(Keyboard.ENTER).isPressed()) {
			int targetUnitIndex = attackableUnitIndexes.get(targetNumber);
			((ICWarsArea)area).attackUnit(targetUnitIndex, unit.getDamage());
			unit.setUsed(true);
			player.centerCamera();
			player.setState(PlayerState.NORMAL);
		}	
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (attackableUnitIndexes.size() != 0) {
			int targetUnitIndex = attackableUnitIndexes.get(targetNumber);
			((ICWarsArea)area).centerCameraOnUnit(targetUnitIndex);
		}
		cursor.setAnchor (canvas.getPosition ().add (1 ,0));
		cursor.draw(canvas);
	}
}