package ch.epfl.cs107.play.game.icwars.area;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.ICWarsBehavior;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor.Faction;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public abstract class ICWarsArea extends Area {
	
	private ICWarsBehavior ICWarsBehavior;
	private final static float CAMERA_SCALE_FACTOR = 10.f;
	private List<Unit> units;
	
    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();

    /// EnigmeArea extends Area

    @Override
    public final float getCameraScaleFactor() {
        return CAMERA_SCALE_FACTOR;
    }

    public abstract DiscreteCoordinates getPlayerSpawnPosition();
    public abstract DiscreteCoordinates getEnemyPlayerSpawnPosition();
    
    /// Demo2Area implements Playable

    public void addUnit(Unit unit) {
    	registerActor(unit);
    	units.add(unit);
    }
    
    public List<Integer> getAttackable(Vector attackerPosition, int radius, Faction faction) {
    	List<Integer> unitIndexes = new ArrayList<Integer>();
    	int maxX = (int)attackerPosition.x + radius;
    	int minX = (int)attackerPosition.x - radius;
    	int maxY = (int)attackerPosition.y + radius;
    	int minY = (int)attackerPosition.y - radius;
    	int index = 0;
    	for (Unit unit:units) {
    		Vector position = unit.getPosition();
    		if (unit.getFaction()==faction
    			&& position.x < maxX
    			&& position.x > minX
    			&& position.y < maxY
    			&& position.y > minY) {
    			unitIndexes.add(index);
    		}
    		index++;
    	}
    	return unitIndexes;
    }
    
    public void attackUnit(int unitIndex, int receivedDamage) {
    	Unit unit = units.get(unitIndex);
    	int damage = receivedDamage - unit.getDefense();
    	if (damage > 0) {
    		unit.damage(damage);
    	}
    }
    
    public void centerCameraOnUnit(int unitIndex) {
    	Unit unit = units.get(unitIndex);
    	setViewCandidate(unit);
    }
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
        	ICWarsBehavior = new ICWarsBehavior(window, getTitle());
            setBehavior(ICWarsBehavior);
            createArea();
            units = new ArrayList<Unit>();
            return true;
        }
        return false;
    }
}
