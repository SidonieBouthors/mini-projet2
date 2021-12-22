package ch.epfl.cs107.play.game.icwars.Menu;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class GameOver extends ICWarsArea {

    private MenuText title;
    private MenuText subtitle;
    private MenuText gameover;

    @Override
    public String getTitle() {
        return "icwars/GameOver";
    }

    protected void createArea() {

        title = new MenuText(this, new DiscreteCoordinates(10, 1), "icwars/title", 5f, 1.3f);
        subtitle = new MenuText(this, new DiscreteCoordinates(6, 1), "icwars/subtitle", 6.3f, .5f);
        gameover = new MenuText(this, new DiscreteCoordinates(9, 0), "icwars/gameOver", 6f, .5f,new Vector(0,0.35f));
        
        registerActor(new Background(this));

        title.enterArea(this,new DiscreteCoordinates(7,7));
        subtitle.enterArea(this,new DiscreteCoordinates(7,6));
        gameover.enterArea(this,new DiscreteCoordinates(7,0));
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

    

}
