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
    private ICWarsFaction type;

    ICWarsActor(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICWarsFaction type) {
        super(owner,orientation,coordinates);
        this.type=type;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public enum ICWarsFaction {
        NONE(0),
        ALLY(1),
        ENEMIE(2);

        final int faction;

        ICWarsFaction(int faction) {
            this.faction=faction;
        }

    }
    public enum ICWarsType{
        NONE(0,0,0),
        SOLDIER(2,2,5),
        TANK(4,7,10);

        final int rayon;
        final int damage;
        final int hpMax;

        ICWarsType(int rayon,int damage, int hpMax) {
            this.rayon = rayon;
            this.damage=damage;
            this.hpMax = hpMax;
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
    public class Unit implements Interactable, Interactor {
        private String name;
        private int hp;


        public void update(float deltaTime) {
            ICWarsActor.this.update(deltaTime);
            if (hp < 0) {
                hp=0;
            }

        }
        public void getDamaged() {
        }

        public int getHp() {
            return hp;
        }

        @Override
        public List<DiscreteCoordinates> getCurrentCells() {
            return Collections.singletonList(getCurrentMainCellCoordinates());
        }

        @Override
        public List<DiscreteCoordinates> getFieldOfViewCells() {
            return null;
        }

        @Override
        public boolean wantsCellInteraction() {
            return true;
        }

        @Override
        public boolean wantsViewInteraction() {
            return false;
        }

        @Override
        public void interactWith(Interactable other) {

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

        @Override
        public void onLeaving(List<DiscreteCoordinates> coordinates) {

        }

        @Override
        public void onEntering(List<DiscreteCoordinates> coordinates) {

        }
    }
}
