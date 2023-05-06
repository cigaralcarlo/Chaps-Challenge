package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for the ExitLock
 *
 */
public class ExitLockTile extends Tile{

	/**
	 * Default constructor
	 */
	public ExitLockTile() {
		super();
		walkable = false;
	}


	@Override
	public String toString() {
		return "ExitLock";
	}

	@Override
	public String getFileName() {
		return "exitLock";
	}

}
