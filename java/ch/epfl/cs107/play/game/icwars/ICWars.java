package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;

import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.MapICwars.Level0;
import ch.epfl.cs107.play.game.icwars.area.MapICwars.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {
	
	private final static int NUMBERS_OF_LEVELS = 2;
	private int currentLevelPassed;

	private RealPlayer player;
	private final String[] areas = {"icwars/Level0", "icwars/Level1"};
	private Unit unit;
	
	private int areaIndex;
	/**
	 * Add all the areas
	 */
	private void createAreas(){

		addArea(new Level0());
		addArea(new Level1());

	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {


		if (super.begin(window, fileSystem)) {
			createAreas();
			areaIndex = 0;
			initArea(areas[areaIndex]);
			return true;
		}
		return false;
	}
	
	 private void initArea(String areaKey) {
		 
		  ICWarsArea area = (ICWarsArea)setCurrentArea(areaKey, true);
		  DiscreteCoordinates coords = area.getPlayerSpawnPosition();
		  player = new RealPlayer(area, Orientation.DOWN, coords,"icwars/allyCursor");
		  player.enterArea(area, coords);
	      player.centerCamera();
		  currentLevelPassed=0;
		 
	 }
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		//Dealing with ending of the game and switching levels
		Keyboard keyboard= getCurrentArea().getKeyboard();
		if (keyboard.get(Keyboard.N).isPressed()) {
			switchArea();
		}
		if (keyboard.get(Keyboard.R).isPressed()) {
			initArea("icwars/Level0");
		}

	}

	@Override
	public void end() {
		System.out.println("Game Over");
	}

	@Override
	public String getTitle() {
		return "ICWars";
	}

	protected void switchArea() {

		player.leaveArea();

		areaIndex = (areaIndex==0) ? 1 : 0;

		ICWarsArea currentArea = (ICWarsArea)setCurrentArea(areas[areaIndex], false);
		player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());

		//Dealing with ending of the game
		currentLevelPassed++;
		if (currentLevelPassed >= NUMBERS_OF_LEVELS) {
			end();
		}


	}

}
