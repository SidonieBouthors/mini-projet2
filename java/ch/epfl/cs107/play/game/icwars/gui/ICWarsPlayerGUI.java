package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior.CellType;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics{

	private RealPlayer player;
	protected Unit selectedUnit;
	protected Unit currentUnit;
	protected CellType currentCellType;
	private DiscreteCoordinates cursorCoordinates;
	public final static float FONT_SIZE = 20f;
	private ICWarsActionsPanel actionPanel;
	private ICWarsInfoPanel infoPanel;
	
	/**
	 * Default ICWarsPlayerGUI Constructor
	 * @param cameraScaleFactor	(float)
	 * @param player			(ICWarsPlayer)
	 */
	public ICWarsPlayerGUI ( float cameraScaleFactor , ICWarsPlayer player ) {
		this.player=(RealPlayer)player;
		this.actionPanel = new ICWarsActionsPanel(cameraScaleFactor);
		this.infoPanel = new ICWarsInfoPanel(cameraScaleFactor);
	}
	
	/**
	 * Setter for selectedUnit (unit selected by player)
	 * @param unit
	 */
	public void setSelectedUnit(Unit unit) {
		selectedUnit = unit;
	}
	/**
	 * Setter for currentUnit (unit over which player is)
	 * @param unit
	 */
	public void setCurrentUnit(Unit unit) {
		currentUnit = unit;
	}
	/**
	 * Setter for type of current cell (cell over which player is)
	 * @param type
	 */
	public void setCurrentCellType (CellType type) {
		currentCellType = type;
	}
	
	@Override
	public void draw(Canvas canvas) {

		cursorCoordinates = player.getCoordinates();

		//draw range and path
		if (selectedUnit != null 
			&& player.getState() == PlayerState.MOVE_UNIT) {
			selectedUnit.drawRangeAndPathTo(cursorCoordinates, canvas);
		}
		//draw action panel
		if (selectedUnit != null 
			&& player.getState() == PlayerState.ACTION_SELECTION) {
			actionPanel.setActions(selectedUnit.getActions());
			actionPanel.draw(canvas);
		}
		//draw info panel
		if (player.getState() == PlayerState.NORMAL 
			|| player.getState() == PlayerState.SELECT_CELL) {
			infoPanel.setCurrentCell(currentCellType);
			infoPanel.setUnit(currentUnit);
			infoPanel.draw(canvas);
		}
	}
}
