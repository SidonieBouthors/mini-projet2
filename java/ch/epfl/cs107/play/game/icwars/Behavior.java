package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class Behavior extends AreaBehavior {
	public enum ICWarsCellType {
		//https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
		NULL(0, false),
		WALL(-16777216, false),
		IMPASSABLE(-8750470, false),
		INTERACT(-256, true),
		DOOR(-195580, true),
		WALKABLE(-1, true),;

		final int type;
		final boolean isWalkable;

		ICWarsCellType(int type, boolean isWalkable){
			this.type = type;
			this.isWalkable = isWalkable;
		}

		public static ICWarsCellType toType(int type){
			for(ICWarsCellType ict : ICWarsCellType.values()){
				if(ict.type == type)
					return ict;
			}
			// When you add a new color, you can print the int value here before assign it to a type
			System.out.println(type);
			return NULL;
		}
	}

	/**
	 * Default Tuto2Behavior Constructor
	 * @param window (Window), not null
	 * @param name (String): Name of the Behavior, not null
	 */
	public Behavior(Window window, String name){
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width ; x++) {
				ICWarsCellType color = ICWarsCellType.toType(getRGB(height-1-y, x));
				setCell(x,y, new Tuto2Cell(x,y,color));
			}
		}
	}
	
	/**
	 * Cell adapted to the Tuto2 game
	 */
	public class Tuto2Cell extends AreaBehavior.Cell {
		/// Type of the cell following the enum
		private final ICWarsCellType type;
		
		/**
		 * Default Tuto2Cell Constructor
		 * @param x (int): x coordinate of the cell
		 * @param y (int): y coordinate of the cell
		 * @param type (EnigmeCellType), not null
		 */
		public  Tuto2Cell(int x, int y, ICWarsCellType type){
			super(x, y);
			this.type = type;
		}
	
		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			return true;
	    }

	    
		@Override
		public boolean isCellInteractable() {
			return true;
		}

		@Override
		public boolean isViewInteractable() {
			return false;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
		}

	}
}

