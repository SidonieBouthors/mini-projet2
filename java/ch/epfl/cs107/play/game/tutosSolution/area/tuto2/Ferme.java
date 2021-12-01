package ch.epfl.cs107.play.game.tutosSolution.area.tuto2;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Specific area
 */
public class Ferme extends Tuto2Area {
	
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

