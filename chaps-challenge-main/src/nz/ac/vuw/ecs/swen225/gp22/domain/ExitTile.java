package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for the Exit 
 *
 */
public class ExitTile extends Tile{

	/**
	 * Default constructor
	 */
	public ExitTile() {
		super();
		
	}
	
	@Override
	public String toString() {
		return "Exit";
	}

	@Override
	public String getFileName() {
		return "exitTile";
	}

}
