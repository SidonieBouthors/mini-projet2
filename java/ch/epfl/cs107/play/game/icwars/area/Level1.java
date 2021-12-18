package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Specific area
 */
public class Level1 extends ICWarsArea {

	@Override
	public String getTitle() {
		return "icwars/Level1";
	}

	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(2, 5);
	}
	@Override
	public DiscreteCoordinates getEnemyPlayerSpawnPosition() {
            return new DiscreteCoordinates(17,5);
        }

	protected void createArea() {
		// Base

		registerActor(new Background(this));

	}
}