package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for Info tile
 *
 */
public class InfoTile extends Tile {
	
	private String text;
	private boolean displayText;

	/**
	 * Constructor takes the text which it displays as a parameter
	 * 
	 * @param t
	 */
	public InfoTile(String t) {
		super();
		text = t;
		displayText = false;
		
	}

	/**
	 * @return the InfoTiles stored text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * return if the text should be displayed
	 * 
	 * @param b
	 */
	public void changeDisplay(boolean b) {
		displayText = b;
	}
	
	/**
	 * @return is the text displaying
	 */
	public boolean isDiplaying() {
		return displayText;
	}
	
	
	@Override
	public String toString() {
		return "Info";
	}

	@Override
	public String getFileName() {
		return "infoTile";
	}

}
