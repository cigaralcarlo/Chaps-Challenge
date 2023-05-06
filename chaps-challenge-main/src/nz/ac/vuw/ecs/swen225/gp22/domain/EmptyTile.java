package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for Empty tiles
 *
 */
public class EmptyTile extends Tile{

	/**
	 * default constructor
	 */
	public EmptyTile() {
		super();
	}
	
	@Override
	public String toString() {
		return "Empty";
	}

	@Override
	public String getFileName() {
		return "freeTile";
	}

}
