package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for the Treasure Tile
 *
 */
public class TreasureTile extends Tile{

	/**
	 * Default constructor
	 */
	public TreasureTile() {
		super();	
	}
	
	@Override
	public String toString() {
		return "Treasure";
	}

	@Override
	public String getFileName() {
		return "treasureTile";
	}

}
