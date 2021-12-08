package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.security.Key;

public class ICWarsPlayerGUI implements Graphics{


	private RealPlayer player;
	protected Unit selectedUnit;
	private DiscreteCoordinates cursorCoordinates;

	public ICWarsPlayerGUI ( float cameraScaleFactor ,
			ICWarsPlayer player ) {
		assert (player.getClass() == RealPlayer.class);
		this.player=(RealPlayer) player;
	}
 
	@Override
	public void draw(Canvas canvas) {
		player.setGUIInfo(this);

		if (selectedUnit != null && selectedUnit.getPosition() == player.getPosition()) {
			selectedUnit.drawRangeAndPathTo(cursorCoordinates, canvas);
		}
	}
	
	public void setSelectedUnit(Unit unit){
		selectedUnit = unit;
	}
	
	public void setCursorCoordinates(DiscreteCoordinates coordinates) {
		cursorCoordinates = coordinates;
	}
	
}
