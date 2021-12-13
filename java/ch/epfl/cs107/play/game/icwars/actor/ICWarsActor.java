package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class ICWarsActor extends MovableAreaEntity {

    protected Faction faction;

    public ICWarsActor(Area owner, DiscreteCoordinates coordinates, Faction faction) {
        super(owner,Orientation.UP,coordinates);
        this.faction=faction;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public enum Faction {
        ALLY(1),
        ENEMY(2);

        final int faction;
        Faction(int faction) {
            this.faction=faction;
        }
    }
    
    /**
     * Getter for faction of Actor
     * Non Intrusive getter : an object ICWarsFaction is immutable.
     * @return faction
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * Register the actor in the area specified
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }
    
    /**
     * Unregister the actor in the area specified
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }
    
    @Override
    public void draw(Canvas canvas){}
    
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {return false;}

    @Override
    public boolean isCellInteractable() {return true;}

    @Override
    public boolean isViewInteractable() {return false;}

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {}
}

