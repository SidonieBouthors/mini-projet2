package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.handler.ICWarInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class ICWarsBehavior extends AreaBehavior {
	/**
	 * Default ICWarsBehavior Constructor
	 * @param window (Window), not null
	 * @param name (String): Name of the Behavior, not null
	 */
	public ICWarsBehavior(Window window, String name){
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width ; x++) {
				CellType color = CellType.toType(getRGB(height-1-y, x));
				setCell(x,y, new ICWarsCell(x,y,color));
			}
		}
	}
	
	public enum CellType {
		//https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
		NONE(0, 0),
		ROAD(-16777216, 0),
		PLAIN ( -14112955 , 1),
		WOOD (-65536, 3),
		RIVER ( -16776961 , 0),
		MOUNTAIN (-256, 4),
		CITY(-1,2);

		final int type;
		final int defense;

		CellType(int type, int defense){
			this.type = type;
			this.defense = defense;
		}

		public static CellType toType(int type){
			for(CellType ict : CellType.values()){
				if(ict.type == type)
					return ict;
			}
			// When you add a new color, you can print the int value here before assign it to a type
			System.out.println(type);
			return NONE;
		}
		
		public int getDefenseStar() {
			return this.defense;
		}

	}

	/**
	 * Cell adapted to the ICWars game
	 */
	public class ICWarsCell extends AreaBehavior.Cell {
		/// Type of the cell following the enum
		private final CellType type;

		/**
		 * Default ICWarsCell Constructor
		 * @param x (int): x coordinate of the cell
		 * @param y (int): y coordinate of the cell
		 * @param type (EnigmeCellType), not null
		 */
		public  ICWarsCell(int x, int y, CellType type){
			super(x, y); 
			this.type = type;
		}
		
		public int getDefense() {
			return type.getDefenseStar();
		}
		
		public CellType getCellType() {
			return type;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			//Checking if there exists an interactable that already take the cell space in the cell, if yes you cannot enter
			for (Interactable interactable : entities) {
				if (interactable.takeCellSpace() && entity.takeCellSpace() && interactable != entity) {
					return false;
				}
			}
			return true;
	    }
		
		@Override
		protected boolean canLeave(Interactable entity) {return true;}
	    
		@Override
		public boolean isCellInteractable() {return true;}

		@Override
		public boolean isViewInteractable() {return false;}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
			((ICWarInteractionVisitor)v).interactWith(this);
		}
	}
}

