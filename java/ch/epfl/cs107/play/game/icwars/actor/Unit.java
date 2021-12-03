package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Unit extends ICWarsActor implements Interactor, Interactable {
    protected Sprite sprite;
    protected String spriteName;
    protected int currentHp;
    protected int maxHp;
    protected int maxDamage;
    protected int radius;

    public Unit(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICWarsFaction faction) {
        super(owner, orientation, coordinates, faction);
        sprite = new Sprite(this.getName(), 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
    }

    public void repairing(int repair) {
        if ((currentHp += repair) > maxHp) {
            currentHp = maxHp;
            currentHp += repair;
        }
    }

    public int getRadius() {
        return radius;
    }

    public int getDamage() {
        return maxDamage;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
    public String getName() {
        return spriteName;
    }

    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }


    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    public void getDamaged(int damage){

        if ((currentHp -= damage) < 0) {
            currentHp=0;
        }else {
            currentHp -= damage;
        }
    }






    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {

    }
}
