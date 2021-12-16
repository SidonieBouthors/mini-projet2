package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends Action{

	public Wait(Unit unit, Area area) {
		super(unit, area);
		name = "(W)ait";
		key = Keyboard.W;
	}
	
	@Override
	public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
		unit.setUsed(true);
		player.setState(PlayerState.NORMAL);
	}
	
	@Override
	public void draw(Canvas canvas) {}
}
