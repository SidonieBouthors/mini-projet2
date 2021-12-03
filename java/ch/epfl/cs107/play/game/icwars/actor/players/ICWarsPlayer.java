package ch.epfl.cs107.play.game.icwars.actor.players;

import java.util.ArrayList;
import java.util.Arrays;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ICWarsPlayer extends ICWarsActor{
	private ArrayList<Unit> units;

	public ICWarsPlayer(Area owner, DiscreteCoordinates coordinates, ICWarsFaction faction, Unit... units) {
		super(owner, coordinates, faction);
		this.units = new ArrayList<Unit>(Arrays.asList(units));
	}
	
    /**Centres the camera on the player
     */
	public void centerCamera() {
		getOwnerArea().setViewCandidate(this);
	}
	/**Checks if player has been defeated
	 * @return whether the player is defeated
	 */
	public boolean isDefeated() {
		if(units.size() == 0) {
			return true;
		}
		else { return false;}
	}
	@Override
	public void update(float deltaTime){
	    super.update(deltaTime);
        if(this.isDisplacementOccurs()){
            for(int i =0; i < units.size();++i){
                if( units.get(i).getHP() ==0 ){
                    units.remove(i);
                }
            }
	    }
	}
    @Override
    public void leaveArea(){
    	Area area = getOwnerArea();
            for (Unit unit:units) {
                area.unregisterActor(unit);
            }
            area.unregisterActor(this);
    }
    @Override
    public void enterArea(Area area, DiscreteCoordinates position){
    	for(Unit unit :units){
            area.registerActor(unit);
        }
    	area.registerActor(this);
    	setOwnerArea(area);
        setCurrentPosition(position.toVector());
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
}
