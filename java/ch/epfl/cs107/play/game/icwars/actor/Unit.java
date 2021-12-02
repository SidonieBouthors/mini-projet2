package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;
import java.util.Locale;

public class Unit extends ICWarsActor implements Interactable,Interactor {
    private String name;
    private int hp;
    private int damage;
    private ICWarsType type;
    private Sprite sprite;

    public Unit(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICWarsFaction faction, ICWarsType type) {
        super(owner, orientation, coordinates, faction);
        this.type=type;
        sprite = new Sprite(this.getName(), 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public enum ICWarsType{
        SOLDIER(2,2,5,"Soldier"),
        TANK(4,7,10,"Tank");

        final int rayon;
        final int damage;
        final int hpMax;
        final String endNameSprite;

        ICWarsType(int rayon,int damage, int hpMax, String endNameSprite) {
            this.rayon = rayon;
            this.damage=damage;
            this.hpMax = hpMax;
            this.endNameSprite = endNameSprite;
        }
    }


    public int getHp() {
        return hp;
    }

    public String getName() {
        return faction.beginSpriteName + type.endNameSprite;

    }
    public void getDamaged(int damage){
        if ((hp -= damage) < 0) {
            hp=0;
        }else {
            hp -= damage;
        }
    }
    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    public void repairing(int repair) {
        if ((hp += repair) > ICWarsType.TANK.hpMax) {
            hp = ICWarsType.TANK.hpMax;
        } else {
            hp += repair;
        }
    }

    public int getDamage(ICWarsType type) {
        return type.damage;
    }



    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return super.getCurrentCells();
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
