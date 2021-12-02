package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class ICWarsActor extends MovableAreaEntity {

    protected ICWarsFaction faction;

    ICWarsActor(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner,orientation,coordinates);
        this.faction=faction;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public enum ICWarsFaction {
        NONE(0,""),
        ALLY(1,"icwars/friendly"),
        ENEMIE(2,"icwars/enemy");

        final int faction;
        final String beginSpriteName;

        ICWarsFaction(int faction, String beginSpriteName) {
            this.faction=faction;
            this.beginSpriteName=beginSpriteName;
        }

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

    }

}

