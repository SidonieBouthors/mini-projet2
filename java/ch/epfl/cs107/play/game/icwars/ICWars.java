package ch.epfl.cs107.play.game.icwars;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.Menu.MenuText;
import ch.epfl.cs107.play.game.icwars.Menu.OpeningMenu;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.players.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer.PlayerState;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Ambulance;
import ch.epfl.cs107.play.game.icwars.actor.unit.Rocket;
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
	
	private ICWarsArea area;
	private ICWarsPlayer enemyPlayer;
	private ICWarsPlayer allyPlayer;
	private ICWarsPlayer neutralPlayer;
	private final String[] areas = {"icwars/Menu1","icwars/Level0", "icwars/Level1"};
	private int areaIndex;
	private List<ICWarsPlayer> playerForThisOne, playerForTheNext;
	private ICWarsPlayer currentPlayer;
	private GameState gameState;
	private DiscreteCoordinates playerCoords;
	private DiscreteCoordinates enemyCoords;
	private DiscreteCoordinates neutralCoords;
	private Soldier allySoldier,neutralSoldier,enemySoldier;
	private Tank allyTank,neutralTank,enemyTank;
	private Rocket allyRocket,neutralRocket,enemyRocket;
	private Ambulance allyAmbulance,neutralAmbulance,enemyAmbulance;
	private OpeningMenu openingMenu;

	
	/**
	 * Add all the areas
	 */
	private void createAreas(){
		openingMenu = new OpeningMenu();
		addArea(openingMenu);
		addArea(new Level0());
		addArea(new Level1());
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			createAreas();
			areaIndex = 0;
			initArea(areas[areaIndex]);
			gameState = GameState.MENU;
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

		playerForThisOne = new ArrayList<>();
		playerForTheNext = new ArrayList<>();

		if (area != openingMenu) {
			
			enemyCoords= area.getEnemyPlayerSpawnPosition();
			neutralCoords = area.getNeutralPlayerSpawnPosition();
			playerCoords = area.getPlayerSpawnPosition();
			
			//create units for all 3 players
			allyTank = new Tank(area, new DiscreteCoordinates(playerCoords.x - 1, playerCoords.y), Faction.ALLY);
			allySoldier = new Soldier(area, playerCoords, Faction.ALLY);
			allyRocket = new Rocket(area, new DiscreteCoordinates(playerCoords.x + 1, playerCoords.y), Faction.ALLY);
			allyAmbulance = new Ambulance(area, new DiscreteCoordinates(playerCoords.x, playerCoords.y + 1), Faction.ALLY);

			enemyTank = new Tank(area, new DiscreteCoordinates(enemyCoords.x - 1, enemyCoords.y), Faction.ENEMY);
			enemySoldier = new Soldier(area, enemyCoords, Faction.ENEMY);
			enemyRocket = new Rocket(area, new DiscreteCoordinates(enemyCoords.x + 1, enemyCoords.y), Faction.ENEMY);
			enemyAmbulance = new Ambulance(area, new DiscreteCoordinates(enemyCoords.x, enemyCoords.y + 1), Faction.ENEMY);
			
			neutralTank = new Tank(area, new DiscreteCoordinates(neutralCoords.x - 1, neutralCoords.y), Faction.NEUTRAL);
			neutralSoldier = new Soldier(area, neutralCoords, Faction.NEUTRAL);
			neutralRocket = new Rocket(area, new DiscreteCoordinates(neutralCoords.x + 1, neutralCoords.y), Faction.NEUTRAL);
			neutralAmbulance = new Ambulance(area, new DiscreteCoordinates(neutralCoords.x, neutralCoords.y + 1), Faction.NEUTRAL);
			neutralPlayer = new AIPlayer(area, enemyCoords, Faction.NEUTRAL, neutralRocket, neutralSoldier, neutralTank, neutralAmbulance);

			//Create AIPlayer and RealPlayer based on Faction selected in the Menu
			if (openingMenu.getFactionChosen() == Faction.ENEMY) {
				allyPlayer = new AIPlayer(area, playerCoords, Faction.ALLY, allyTank, allySoldier,allyAmbulance,allyRocket);
				enemyPlayer = new RealPlayer(area, enemyCoords, Faction.ENEMY, enemySoldier, enemyTank,enemyAmbulance,enemyRocket);
			 }
			if (openingMenu.getFactionChosen() == Faction.ALLY){
				allyPlayer = new RealPlayer(area, playerCoords, Faction.ALLY, allyTank, allySoldier,allyAmbulance,allyRocket);
				enemyPlayer = new AIPlayer(area, enemyCoords, Faction.ENEMY, enemySoldier, enemyTank,enemyAmbulance,enemyRocket);
			}
			//add players to player list
			playerForThisOne.add(allyPlayer);
			playerForThisOne.add(enemyPlayer);
			playerForThisOne.add(neutralPlayer);
			//players enter area
			neutralPlayer.enterArea(area, neutralPlayer.getCoordinates());
			enemyPlayer.enterArea(area, enemyPlayer.getCoordinates());
			allyPlayer.enterArea(area, allyPlayer.getCoordinates());
		}
	 }
	
	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		//Dealing with ending of the game and switching levels
		Keyboard keyboard= getCurrentArea().getKeyboard();
		if (keyboard.get(Keyboard.N).isPressed()&& gameState!=GameState.MENU) {
			if (areaIndex + 1 < areas.length) {
				areaIndex++;
				gameState = GameState.INIT;
			} else { 
				gameState=GameState.GAME_OVER;
			}
		}
		if (keyboard.get(Keyboard.R).isPressed() && gameState!=GameState.MENU) {
			areaIndex = 1;
			gameState = GameState.INIT;
		}

		switch(gameState) {
		case MENU:

			if (openingMenu.getFactionChosen() != null && openingMenu.getFactionChosen() != Faction.NONE) {
				++areaIndex;
				gameState = GameState.INIT;
			}
			break;
		case INIT:
			initArea(areas[areaIndex]);
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
		
			for (Iterator<ICWarsPlayer> iterator = playerForThisOne.iterator(); iterator.hasNext();) {
				ICWarsPlayer player = iterator.next();
            	if(player.isDefeated()) {
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
			else { gameState=GameState.GAME_OVER;}
			break;
		case GAME_OVER:
			end();
			break;
		}
		
	}

	/**
	 * Enum of Game States
	 */
	public enum GameState {
		MENU,
		INIT,
		CHOOSE_PLAYER,
		START_PLAYER_TURN,
		PLAYER_TURN,
		END_PLAYER_TURN,
		END_TURN,
		END,
		GAME_OVER;
	}
	
	@Override
	public void end() {
		if (!getWindow().isCloseRequested()){
			for (ICWarsPlayer player:playerForThisOne) {
				player.setState(PlayerState.IDLE);
		}
		MenuText gameOver = new MenuText(area, new DiscreteCoordinates(10,5), "icwars/gameOver",10,10,new Vector(-5,-5));
		gameOver.draw(getWindow());
		area.setViewCandidate(gameOver);
		}
	}

	@Override
	public String getTitle() {return "ICWars";}

	/**
	 * Method to switch area (OBSOLETE)
	 
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
	}*/
}
