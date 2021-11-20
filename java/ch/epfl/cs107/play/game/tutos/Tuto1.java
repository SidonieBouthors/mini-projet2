package ch.epfl.cs107.play.game.tutos;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Ferme;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Tuto1 extends AreaGame{
	
	private SimpleGhost player;
	
	private void createAreas() {
		addArea(new Village());
		addArea(new Ferme());
	}
	
	public void switchArea() {
		
		getCurrentArea().unregisterActor(player);
		
		if (getCurrentArea().getTitle()=="zelda/Village") {
			setCurrentArea("zelda/Ferme", false);
		}
		else if (getCurrentArea().getTitle()=="zelda/Ferme") {
			setCurrentArea("zelda/Village", false);
		}
		
		getCurrentArea().registerActor(player);
		getCurrentArea().setViewCandidate(player);
		player.strengthen();
	}
	
	@Override
	public String getTitle() {
		return "Tuto1";
	}
	
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window,  fileSystem)) {
			
			player = new SimpleGhost ( new Vector(18,17), "ghost.1");
			
			createAreas();
			setCurrentArea("zelda/Ferme", true);
			getCurrentArea().registerActor(player);
			getCurrentArea().setViewCandidate(player);
			return true;
		}
		else return false;
	}
	
	@Override
	public void end() {
		
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		//dealing with switching area
		if (player.isWeak()) {
			switchArea();
		}
		
		//dealing with movement
		Keyboard keyboard = getWindow().getKeyboard();
		//UP
		Button key = keyboard.get(Keyboard.UP);
		if (key.isDown()) {
			player.moveUp();
		}
		//DOWN
		key = keyboard.get(Keyboard.DOWN);
		if (key.isDown()) {
			player.moveDown();
		}
		//RIGHT
		key = keyboard.get(Keyboard.RIGHT);
		if (key.isDown()) {
			player.moveRight();
		}
		//LEFT
		key = keyboard.get(Keyboard.LEFT);
		if (key.isDown()) {
			player.moveLeft();
		}
	}
	
}
