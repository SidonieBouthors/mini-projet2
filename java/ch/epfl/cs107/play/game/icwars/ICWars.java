package ch.epfl.cs107.play.game.icwars;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.unit.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.unit.Tank;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {
	
	private final static int NUMBERS_OF_LEVELS = 2;
	private int currentLevelPassed;
	private ICWarsPlayer enemyPlayer;
	private ICWarsPlayer allyPlayer;
	private final String[] areas = {"icwars/Level0", "icwars/Level1"};
	private Unit unit;
	private int areaIndex;
	private List<ICWarsPlayer> playerForThisOne;
	private List<ICWarsPlayer> playerForTheNext;
	private ICWarsPlayer currentPlayer;
	private GameState gameState;
	
	/**
	 * Add all the areas
	 */
	private void createAreas(){
		addArea(new Level0());
		addArea(new Level1());
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			createAreas();
			areaIndex = 0;
			initArea(areas[areaIndex]);
			return true;
		}
		return false;
	}
	
	private void initArea(String areaKey) {

		ICWarsArea area = (ICWarsArea)setCurrentArea(areaKey, true);
		DiscreteCoordinates playerCoords = area.getPlayerSpawnPosition();
		DiscreteCoordinates enemyCoords = area.getEnemyPlayerSpawnPosition();
	  
		playerForThisOne = new ArrayList<ICWarsPlayer>();
		playerForTheNext = new ArrayList<ICWarsPlayer>();
		gameState = GameState.INIT;
	  
		Tank allyTank = new Tank(area, new DiscreteCoordinates(2, 5),Faction.ALLY);
		Soldier allySoldier = new Soldier(area, new DiscreteCoordinates(3, 5),Faction.ALLY);
		Tank enemyTank = new Tank(area, new DiscreteCoordinates(8, 5),Faction.ENEMY);
		Soldier enemySoldier = new Soldier(area, new DiscreteCoordinates(9, 5),Faction.ENEMY);
	
		allyPlayer = new RealPlayer(area, playerCoords,Faction.ALLY, allyTank, allySoldier);
		enemyPlayer = new AIPlayer(area,enemyCoords,Faction.ENEMY,enemySoldier,enemyTank);
	
		enemyPlayer.enterArea(area, enemyCoords);
		allyPlayer.enterArea(area, playerCoords);
		currentLevelPassed=0;
	 }
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		//Dealing with ending of the game and switching levels
		Keyboard keyboard= getCurrentArea().getKeyboard();
		if (keyboard.get(Keyboard.N).isPressed()) {
			switchArea();
		}
		if (keyboard.get(Keyboard.R).isPressed()) {
			initArea("icwars/Level0");
		}

		switch(gameState) {
		case INIT:
			//init
			playerForThisOne.add(allyPlayer);
            playerForThisOne.add(enemyPlayer);
			gameState = GameState.CHOOSE_PLAYER;
			break;
		case CHOOSE_PLAYER:
			if (playerForThisOne.size()==0) {
				gameState = GameState.END_TURN;
			} else {
				currentPlayer = playerForThisOne.get(0);
				playerForThisOne.remove(currentPlayer);
				gameState = GameState.START_PLAYER_TURN;
			}
			break;
		case START_PLAYER_TURN:
			currentPlayer.startTurn();
			gameState = GameState.PLAYER_TURN;
			break;
		case PLAYER_TURN:
			if (currentPlayer.getState() == PlayerState.IDLE) {
				gameState = GameState.END_PLAYER_TURN;
			}
			break;
		case END_PLAYER_TURN:
			if (currentPlayer.isDefeated()) {
				currentPlayer.leaveArea();
			}
			else {
				currentPlayer.setUnitsUsable();
				playerForTheNext.add(currentPlayer);
				gameState = GameState.CHOOSE_PLAYER;
			}
			//veiller a ce que toutes ses unités redeviennent utilisables
			break;
		case END_TURN:
			for (ICWarsPlayer player: playerForThisOne) {
				if (player.isDefeated()) {
					playerForThisOne.remove(player);
				}
			}
			for(ICWarsPlayer player: playerForTheNext) {
				playerForThisOne.add(player);
			}
			//supprimer tout les joueurs défait (de liste des joueurs en attente et diu jeu)
			if (playerForThisOne.size() <2) {
				gameState = GameState.END;
			}
			else {
				gameState = GameState.CHOOSE_PLAYER;
			}
			break;
		case END:
			if (areaIndex < areas.length) {
				switchArea();
				gameState = GameState.INIT;
			} else {
				end();
			}
			break;
		}
	}

	public enum GameState {
		INIT(0),
		CHOOSE_PLAYER(1),
		START_PLAYER_TURN(2),
		PLAYER_TURN(3),
		END_PLAYER_TURN(4),
		END_TURN(5),
		END(6);
		private int state;
		GameState(int state) {
			this.state=state;
		}
	}
	
	@Override
	public void end() {}

	@Override
	public String getTitle() {return "ICWars";}

	protected void switchArea() {

		currentPlayer.leaveArea();
		areaIndex = (areaIndex==0) ? 1 : 0;

		ICWarsArea currentArea = (ICWarsArea)setCurrentArea(areas[areaIndex], false);
		currentPlayer.enterArea(currentArea, currentArea.getPlayerSpawnPosition());

		//Dealing with ending of the game
		currentLevelPassed++;
		if (currentLevelPassed >= NUMBERS_OF_LEVELS) {
			end();
		}
	}
}
