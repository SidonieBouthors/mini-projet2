package ch.epfl.cs107.play.game.icwars.actor.players;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;

import ch.epfl.cs107.play.game.icwars.Menu.OpeningMenu;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class AnimatedPlayer extends ICWarsActor implements Interactor {



    private AnimatedPlayer player;
    public static Sprite[] passiveSprite;
    public static Sprite[] passiveBicycle;
    private Sprite[] currentpassive;
    private String spriteName;
    private ICWarsArea area ;
    private boolean hasChosenAFaction;

    public static Animation[] animCharac;
    public static Animation[] animBicycle;
    private Animation[] currentanimation;

    public final int ANIMATION_DURATION = 8;



    public AnimatedPlayer(Area area, DiscreteCoordinates coordinates, Faction faction) {
        super(area, coordinates,faction);

        spriteName = "player";


        animCharac = constructAnimation(spriteName);
        passiveSprite = constructPassiveSprite(spriteName);
        this.area = (ICWarsArea) area;





        currentanimation = animCharac;
        currentpassive = passiveSprite;
        hasChosenAFaction=false;




    }
    public void switchAnimation(Sprite[] passive, Animation[] animation) {
        currentpassive = passive;
        currentanimation = animation;
    }

    // Special method to construct passive Sprite of "player". If spriteName change you'll probably have to change parameters.

    private Sprite[] constructPassiveSprite(String spriteName) {
        return RPGSprite.extractWithoutAnim(spriteName, 4, 1, 1,this,
                16, 16,1,new Vector(0,0), new Orientation[] {Orientation.DOWN,
                        Orientation.LEFT, Orientation.UP, Orientation.RIGHT});
    }

    // Special method to construct Animation of "player". If spriteName change you'll probably have to change parameters.

    private Animation[] constructAnimation(String spriteName) {
        Sprite[][] sprite = RPGSprite.extractSprites(spriteName, 4, 1, 1,this,
                16, 16, new Orientation[] {Orientation.DOWN,
                        Orientation.LEFT, Orientation.UP, Orientation.RIGHT});
        return Animation.createAnimations(ANIMATION_DURATION/2,sprite);
    }


    @Override
    public void draw(Canvas canvas) {


        Keyboard keyboard = getOwnerArea().getKeyboard();

        //playing the corresponding animations when moving
        if (keyboard.get(Keyboard.DOWN).isDown() || keyboard.get(Keyboard.UP).isDown() || keyboard.get(Keyboard.LEFT).isDown() || keyboard.get(Keyboard.RIGHT).isDown()) {
            currentanimation[getOrientation().ordinal()].draw(canvas);

        } else {
            currentpassive[getOrientation().ordinal()].draw(canvas);
        }

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (isDisplacementOccurs()) {
            area.setViewCandidate(this);
        }

        //Updating animations but no need to update passiveSprite
        currentanimation[getOrientation().ordinal()].update(deltaTime);

        //Dealing with movements
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));

        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));



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
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }



    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setCurrentPosition(position.toVector());
        area.setViewCandidate(this);
        resetMotion();
        setOwnerArea(area);
    }
    public void exitArea(Area area){
        area.unregisterActor(this);

    }
    /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {

        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(ANIMATION_DURATION);
            }
        }

    }










}

