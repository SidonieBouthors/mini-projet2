package ch.epfl.cs107.play.game.tutosSolution.area.tuto2;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.tutosSolution.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

/**
 * Specific area
 */
public class Village extends Tuto2Area {
	
	@Override
	public String getTitle() {
		return "icwars/Level1";
	}
	
	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(10,5);
	}
	
	protected void createArea() {
        // Base
	
        registerActor(new Background(this)) ;
        registerActor(new SimpleGhost(new Vector(20, 10), "ghost.2"));
        }
	
	
    
}
