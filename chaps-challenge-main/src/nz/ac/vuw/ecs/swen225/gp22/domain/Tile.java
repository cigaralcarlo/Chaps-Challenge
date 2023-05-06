package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * The Domain abstract tile class for all tiles within the game
 *
 */
public abstract class Tile{
	protected boolean walkable = true;

	/**
	 * Empty constructor
	 */
	public Tile(){

	}

	/**
	 * is this tile able to be moved onto?
	 * 
	 * @return walkable
	 */
	public boolean isWalkable() {
		return walkable;
	}

	public abstract String toString();
		
	/**
	 * returns the value of the tiles respective filename
	 * 
	 * @return this tiles filename
	 */
	public abstract String getFileName();


}
