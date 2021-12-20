package ch.epfl.cs107.play.game.icwars;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.unit.Tank;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {
	
	private final static int NUMBERS_OF_LEVELS = 2;
	private int currentLevelPassed;
	private ICWarsPlayer enemyPlayer;
	private ICWarsPlayer allyPlayer;
	private final String[] areas = {"icwars/Level0", "icwars/Level1"};
	private int areaIndex;
	private List<ICWarsPlayer> playerForThisOne;
	private List<ICWarsPlayer> playerForTheNext;
	private ICWarsPlayer currentPlayer;
	private GameState gameState;
	private ICWarsArea area;
	
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
			gameState = GameState.INIT;
			return true;
		}
		return false;
	}
	
	/**
	 * Initialise the area given by key, add all players and their units, etc...
	 * @param areaKey
	 */
	private void initArea(String areaKey) {

		area = (ICWarsArea)setCurrentArea(areaKey, true);
		DiscreteCoordinates playerCoords = area.getPlayerSpawnPosition();
		DiscreteCoordinates enemyCoords = area.getEnemyPlayerSpawnPosition();
	  
		playerForThisOne = new ArrayList<>();
		playerForTheNext = new ArrayList<>();
	  
		//create units
		Tank allyTank = new Tank(area, new DiscreteCoordinates(2, 5),Faction.ALLY);
		Soldier allySoldier = new Soldier(area, new DiscreteCoordinates(3, 5),Faction.ALLY);
		Tank enemyTank = new Tank(area, new DiscreteCoordinates(8, 5),Faction.ENEMY);
		Soldier enemySoldier = new Soldier(area, new DiscreteCoordinates(9, 5),Faction.ENEMY);
	
		//create players
		allyPlayer = new AIPlayer(area, playerCoords,Faction.ALLY, allyTank, allySoldier);
		enemyPlayer = new RealPlayer(area,enemyCoords,Faction.ENEMY,enemySoldier,enemyTank);
	
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
			if (areaIndex + 1 < areas.length) {
				areaIndex++;
				gameState = GameState.INIT;
			}
		}
		if (keyboard.get(Keyboard.R).isPressed()) {
			areaIndex = 0;
			gameState = GameState.INIT;
		}

		switch(gameState) {
		case INIT:
			initArea(areas[areaIndex]);
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
			currentPlayer.setUnitsUsable();
			playerForTheNext.add(currentPlayer);
			gameState = GameState.CHOOSE_PLAYER;
			break;
		case END_TURN:

			playerForThisOne.addAll(playerForTheNext);
			playerForTheNext.clear();
			System.out.println(playerForThisOne.toString());
		
			for (Iterator<ICWarsPlayer> iterator = playerForThisOne.iterator(); iterator.hasNext();) {
				ICWarsPlayer player = iterator.next();
            	if(player.isDefeated()) {
					System.out.println("Defeated");
					iterator.remove();
                    currentPlayer.leaveArea();
            	}
            }
			if (playerForThisOne.size() <2) {
				gameState = GameState.END;
			}
			else {
				gameState = GameState.CHOOSE_PLAYER;
			}
			break;
		case END:
			if (areaIndex + 1 < areas.length) {
				areaIndex++;
				gameState = GameState.INIT;
			}
			else { end(); }
			break;
		}
	}

	/**
	 * Enum of Game States
	 */
	public enum GameState {
		INIT(),
		CHOOSE_PLAYER(),
		START_PLAYER_TURN(),
		PLAYER_TURN(),
		END_PLAYER_TURN(),
		END_TURN(),
		END();

		GameState() {
		}
	}
	
	@Override
	public void end() {
		Sprite gameOver = new Sprite("icwars/gameOver", 12, 10, playerForThisOne.get(0));
		area.setViewCandidate(playerForThisOne.get(0));
		gameOver.setAnchor(new Vector(-6,-5));
		gameOver.draw(getWindow());
	}

	@Override
	public String getTitle() {return "ICWars";}

	/**
	 * Method to switch area (OBSOLETE)
	 */
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
