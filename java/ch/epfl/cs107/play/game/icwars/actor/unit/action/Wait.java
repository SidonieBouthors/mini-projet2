package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends Action{

	/**
	 * Wait Constructor
	 * @param unit	(Unit): unit to wait
	 * @param area	(Area): area on which waiting occurs
	 */
	public Wait(Unit unit, Area area) {
		super(unit, area);
		this.setName("(W)ait");
		this.setKey(Keyboard.W);
	}
	
	@Override
	public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
		getUnit().setUsed(true);
		player.setState(PlayerState.NORMAL);
	}
	
	@Override
	public void draw(Canvas canvas) {}

	@Override
	public boolean doAutoAction(float dt, ICWarsPlayer player) {
		getUnit().setUsed(true);
		player.setState(PlayerState.NORMAL);
		return true;
	}
}
