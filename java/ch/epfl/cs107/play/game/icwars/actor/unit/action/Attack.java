package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Attack extends Action{

	Attack(Unit unit, Area area) {
		super(unit, area);
		name = "(A)ttack";
		key = Keyboard.A;
	}
	
	@Override
	public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
		
	}
	
	@Override
	public void draw(Canvas canvas) {}
}