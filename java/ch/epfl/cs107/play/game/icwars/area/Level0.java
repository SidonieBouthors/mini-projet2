package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Specific area
 */
public class Level0 extends ICWarsArea {
	
	@Override
	public  String getTitle() {
		return "icwars/Level0";
	}
	

	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(3,4);
	}
	

    public DiscreteCoordinates getEnemyPlayerSpawnPosition() {
        return new DiscreteCoordinates(8,5);
    }
    
    @Override
	public DiscreteCoordinates getNeutralPlayerSpawnPosition() {
		return new DiscreteCoordinates(5,7);
	}




	@Override
	protected void createArea() {
        registerActor(new Background(this));
	}

}

