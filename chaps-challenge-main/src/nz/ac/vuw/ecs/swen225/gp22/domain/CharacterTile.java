package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * @author roddicadam - 300580773
 * 
 * Abstract class for entities which are permitted to move, only Enemy and Chap inherit this class
 *
 */
public abstract class CharacterTile extends Tile{
	private Tile standingOn;
	private final String name;
	
	
	/**
	 * Constructor which takes the name as a parameter
	 * 
	 * @param name
	 */
	public CharacterTile(String name) {
		super();
		standingOn = new EmptyTile();
		this.name = name;
	}
	
	/**
	 * set the tile this character tile is standing 
     * also check if the tile its standing on is an info tile if
     * so it should display its text
	 * 
	 * @param t
	 */
	public void setStandingOn(Tile t) {
		if (t instanceof InfoTile) {
			((InfoTile) t).changeDisplay(true);
		}
		else if (!(t instanceof InfoTile) && standingOn instanceof InfoTile) {
			((InfoTile) standingOn).changeDisplay(false);
		}
		

		standingOn = t;
		assert standingOn.isWalkable() : "Character is not allowed to step on something which is not walkable";
	}
	
	/**
	 * @return Tile this character is standing on
	 */
	public Tile getStandingOn() {
		return standingOn;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}


	@Override
	public abstract String toString();
	@Override
	public abstract String getFileName();
	
	/**
	 * abstract move method to to handle this characters movement
	 * 
	 * @param m
	 */
	public abstract void move(Maze m);

}
