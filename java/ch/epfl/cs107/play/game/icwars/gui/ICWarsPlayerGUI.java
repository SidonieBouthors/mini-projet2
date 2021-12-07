package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics{

	private ICWarsPlayer player;
<<<<<<< HEAD
	private Unit selectedUnit;
=======

>>>>>>> c0ef8dc2c34f6fa217bc232b667e40f8bd1137bc
	public ICWarsPlayerGUI ( float cameraScaleFactor ,
			ICWarsPlayer player ) {
		this.player = player;
	}

	@Override
	public void draw(Canvas canvas) {
<<<<<<< HEAD
		selectedUnit.drawRangeAndPathTo(null, canvas);
=======
		player;
>>>>>>> c0ef8dc2c34f6fa217bc232b667e40f8bd1137bc
	}
	
	public void setSelectedUnit(Unit unit){
		selectedUnit = unit;
	}
	
}
