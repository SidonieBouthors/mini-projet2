package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.areagame.AreaBehavior.Cell;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;

public interface ICWarInteractionVisitor extends AreaInteractionVisitor {

	/**
	 * Interact with a RealPlayer
	 * @param player	(RealPlayer)
	 */
    default void interactWith(RealPlayer player) {};
    /**
	 * Interact with a Unit
	 * @param unit	(Unit)
	 */
    default void interactWith(Unit unit){};
    /**
	 * Interact with a Cell
	 * @param cell	(Cell)
	 */
	default void interactWith(Cell cell) {};
    
}
