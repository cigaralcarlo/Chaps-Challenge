package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for Wall Tile
 *
 */
public class WallTile extends Tile {

	/**
	 * Default constructor
	 */
	public WallTile() {
		super();
		walkable = false;
	}
	
	@Override
	public String toString() {
		return "wall";
	}

	@Override
	public String getFileName() {
		return "wallTile";
	}
}
