package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics{

	private ICWarsPlayer player;
	private Unit selectedUnit;
	private DiscreteCoordinates cursorCoordinates;
	public ICWarsPlayerGUI ( float cameraScaleFactor ,
			ICWarsPlayer player ) {
		this.player = player;

		// Unité choisit au hasard a enlever dans le futur
		player.selectUnit(0);
	}

	@Override
	public void draw(Canvas canvas) {
		player.setGUIInfo(this);
		selectedUnit.drawRangeAndPathTo(cursorCoordinates, canvas);

	}
	
	public void setSelectedUnit(Unit unit){
		selectedUnit = unit;
	}
	
	public void setCursorCoordinates(DiscreteCoordinates coordinates) {
		cursorCoordinates = coordinates;
	}
	
}
