package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class Action implements Graphics{
	
	protected Unit unit;
	protected Area area;
	protected String name;
	protected int key;
	
	Action(Unit unit, Area area){
		this.unit = unit;
		this.area = area;
	}
	
	public abstract void doAction ( float dt , ICWarsPlayer player , Keyboard keyboard );

}
