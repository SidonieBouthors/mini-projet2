package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Attack extends Action{

	private ImageGraphics cursor;
	
	Attack(Unit unit, Area area) {
		super(unit, area);
		this.name = "(A)ttack";
		this.key = Keyboard.A;
		this.cursor=new ImageGraphics (ResourcePath . getSprite ("icwars/UIpackSheet"),1f, 1f, new RegionOfInterest(4*18 , 26*18 ,16 ,16));
	}
	
	@Override
	public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		
		cursor.setAnchor (canvas.getPosition ().add (1 ,0));
		cursor.draw(canvas);
	}
}