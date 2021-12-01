package ch.epfl.cs107.play.game.icwars.area.MapICwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.area.ICWARSAREA;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Specific area
 */
public class Level0 extends ICWARSAREA {
	
	@Override
	public String getTitle() {
		return "icwars/Level0";
	}
	
	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(5,5);
	}
	
	protected void createArea() {
        // Base
        registerActor(new Background(this));
	}
	
}

