package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class Action implements Graphics{
	
	private Unit unit;
	private Area area;
	private String name;
	private int key;
	
	/**
	 * Action Constructor
	 * @param unit	(Unit): unit associated with the action
	 * @param area	(Area): area on which action occurs
	 */
	Action(Unit unit, Area area){
		this.unit = unit;
		this.area = area;
	}
	
	/**
	 * Getter for action key
	 * @return key	(int): key associated to this action
	 */
	public int getKey() { return key; }
	/**
	 * Setter for action key (Protected)
	 * @param key	(int):key associated to this action
	 */
	protected void setKey(int key) {this.key = key;}
	/**
	 * Getter for action name
	 * @return name	(String): name of action
	 */
	public String getName() { return name; }
	/**
	 * Setter for action name (Protected)
	 * @param name	(String)
	 */
	protected void setName(String name) { this.name = name; }
	/**
	 * Getter for unit (Protected)
	 * @return unit	(Unit)
	 */
	protected Unit getUnit() { return unit; }
	/**
	 * Getter for area (Protected)
	 * @return area	(Area)
	 */
	protected Area getArea() {return area;}
	
	/**
	 * Trigger the events linked to this action (as chosen by player)
	 * @param dt		(float)
	 * @param player	(ICWarsPlayer): player triggering the action
	 * @param keyboard	(Keyboard): keyboard used to trigger events
	 */
	public abstract void doAction ( float dt , ICWarsPlayer player , Keyboard keyboard );
	/**
	 * Trigger the events linked to this action (automatically)
	 * @param dt				(float)
	 * @param player			(ICWarsPlayer): player triggering action (AI)
	 * @return actionExecuted	(boolean): whether the action was able to be executed or not
	 */
	public abstract boolean doAutoAction(float dt, ICWarsPlayer player);

}
