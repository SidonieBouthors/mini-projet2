package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics{
	private ICWarsPlayer player;
	public ICWarsPlayerGUI ( float cameraScaleFactor ,
			ICWarsPlayer player ) {
		this.player = player;
	}

	@Override
	public void draw(Canvas canvas) {
		player.unit.drawRangeAndPathTo();
	}
}
