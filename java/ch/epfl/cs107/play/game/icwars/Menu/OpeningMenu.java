package ch.epfl.cs107.play.game.icwars.Menu;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.players.AnimatedPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Tank;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class OpeningMenu extends ICWarsArea {

    private AnimatedPlayer menuPlayer;
    private Tank allyMenuTank;
    private Tank enemyMenuTank;
    private MenuText title;
    private MenuText subtitle;
    private MenuText instruction;
    private ICWarsActor.Faction factionChosen;

    @Override
    public String getTitle() {
        return "icwars/Menu1";
    }


    protected void createArea() {
        menuPlayer = new MenuPlayer(this,new DiscreteCoordinates(7,1), ICWarsActor.Faction.NONE);
        
        title = new MenuText(this, new DiscreteCoordinates(9, 1), "icwars/title", 5f, 1.3f);
        subtitle = new MenuText(this, new DiscreteCoordinates(9, 1), "icwars/subtitle", 6.3f, .5f);
        instruction = new MenuText(this, new DiscreteCoordinates(9, 1), "icwars/selectTeam", 6f, .4f);
        
        registerActor(new Background(this));

        allyMenuTank = new Tank(this, new DiscreteCoordinates(1, 1), ICWarsActor.Faction.ALLY,3.f,3.f,new Vector(0,-0.75f));
        enemyMenuTank = new Tank(this, new DiscreteCoordinates(10, 1), ICWarsActor.Faction.ENEMY,3.f,3.f,new Vector(-2f,-0.75f));

        enemyMenuTank.enterArea(this, new DiscreteCoordinates(16, 1));
        allyMenuTank.enterArea(this, new DiscreteCoordinates(3,1));
        menuPlayer.enterArea(this,new DiscreteCoordinates(10,1));
        title.enterArea(this,new DiscreteCoordinates(7,6));
        subtitle.enterArea(this,new DiscreteCoordinates(7,5));
        instruction.enterArea(this,new DiscreteCoordinates(7,1));
    }

    public ICWarsActor.Faction getFactionChosen() {
        return factionChosen;
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return DiscreteCoordinates.ORIGIN;
    }

    @Override
    public DiscreteCoordinates getEnemyPlayerSpawnPosition() {
        return DiscreteCoordinates.ORIGIN;
    }

    @Override
    public DiscreteCoordinates getNeutralPlayerSpawnPosition() {
        return DiscreteCoordinates.ORIGIN;
    }


    private class MenuPlayer extends AnimatedPlayer{

        private final ICWarsMenuPlayerInteractionHandler handler;

        public MenuPlayer(Area area, DiscreteCoordinates coordinates, Faction faction) {
            super(area, coordinates, faction);
            this.handler= new ICWarsMenuPlayerInteractionHandler();
        }

        public void interactWith(Interactable other) {
            if (!isDisplacementOccurs()) {
                other.acceptInteraction(handler);
            }
        }

        private class ICWarsMenuPlayerInteractionHandler implements ICWarInteractionVisitor {

            @Override
            public void interactWith(Unit unit) {
                if (unit.getFaction() == Faction.ALLY) {
                    factionChosen=Faction.ALLY;

                //Half Useless condition, but there are 3 Factions and if we want to add more units in the menu later it would be better
                }else if(unit.getFaction() == Faction.ENEMY){
                    factionChosen=Faction.ENEMY;
                }
                System.out.println("interagit");

            }


        }
    }

}
