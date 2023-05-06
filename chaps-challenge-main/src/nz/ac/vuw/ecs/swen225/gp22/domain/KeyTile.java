package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for Key Tile
 *
 */
public class KeyTile extends Tile{
	
	private String color;

	/**
	 * Constructor takes the color of the key as a parameter
	 * 
	 * @param keyColor
	 */
	public KeyTile(String keyColor) {
		super();
		color = keyColor;
	}
	
	/**
	 * @return this keys color
	 */
	public String getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return "Key";
	}

	@Override
	public String getFileName() {
		return "keyTile_" + color;
	}

}
