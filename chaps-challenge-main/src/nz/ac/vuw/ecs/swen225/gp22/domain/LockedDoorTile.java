package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for the LockedDoor
 *
 */
public class LockedDoorTile extends Tile{
	
	private String color;

	/**
	 * Constructor takes the color of this locked door as a parameter
	 * 
	 * @param keyColor
	 */
	public LockedDoorTile(String keyColor) {
		super();
		walkable = false;
		color = keyColor;
	}
	
	/**
	 * @return this doors color
	 */
	public String getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return "LockedDoor";
	}

	@Override
	public String getFileName() {
		return "lockedDoor_" + color;
	}
}
