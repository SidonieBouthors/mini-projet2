package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.Cursor;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.MapICwars.Level0;
import ch.epfl.cs107.play.game.icwars.area.MapICwars.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {
	
	public final static float CAMERA_SCALE_FACTOR = 10.f;

	private Cursor player;
	private final String[] areas = {"icwars/Level0", "icwars/Level1"};
	
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
		  player = new Cursor(area, Orientation.DOWN, coords,"icwars/allyCursor");
		  player.enterArea(area, coords);
	      player.centerCamera();
		 
	 }
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

	}

	@Override
	public void end() {
	}

	@Override
	public String getTitle() {
		return "ICWARS";
	}

	protected void switchArea() {

		player.leaveArea();

		areaIndex = (areaIndex==0) ? 1 : 0;

		ICWarsArea currentArea = (ICWarsArea)setCurrentArea(areas[areaIndex], false);
		player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());

	}

}
