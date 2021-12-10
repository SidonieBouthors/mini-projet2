package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;

public interface ICWarInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(RealPlayer player) {};
    default void interactWith(Unit unit){};

}
