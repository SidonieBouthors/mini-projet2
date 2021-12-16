package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.areagame.AreaBehavior.Cell;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;

public interface ICWarInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(RealPlayer player) {};
    default void interactWith(Unit unit){};
	default void interactWith(Cell cell) {};
    
}
