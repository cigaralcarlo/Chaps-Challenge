package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.*;

import nz.ac.vuw.ecs.swen225.gp22.persistency.XMLLoader;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Animate;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Sound;

/**
 * @author roddicadam - 300580773
 * 
 * The Maze class holds all the tiles together and provides their positions and contains
 * methods which control their movement aswell as the tile and game states.
 *
 */
@SuppressWarnings("deprecation")
public class Maze implements Observer{

	/**
	 * enum for the directions characters can move
	 * 
	 * @author roddicadam - 300580773
	 */
	public enum direction{
		UP,
		DOWN,
		LEFT,
		RIGHT,
		NULL
	}

	private Tile[][] board;
	private ChapTile chap;
	
	private CharacterTile current;
	private Sound sound;
	private int level;

	private int totalTreasureCount;
	private int availableTreasure;
	private int collectedTreasure;

	private final int width, height;
	
	private boolean hasWon = false;
	private boolean hasLost = false;
	private boolean mazeStarted = true;

	/**
	 * Maze constructor takes the width and height of the maze as parameters
	 * aswell as the number of the level for this maze.
	 * 
	 * @param width
	 * @param height
	 * @param level
	 */
	public Maze(int width, int height, int level) {

		this.width = width;
		this.height = height;
		this.level = level;
		this.sound = new Sound();

		assert width > 0 && height > 0 : "width and height should not be negative or zero";

		board = new Tile[width][height];
	}

	/**
	 * method to check if something movable can move to a specific tile and if so move it
	 * 
	 * @param t
	 * @param x
	 * @param y
	 */
	public void moveTile(CharacterTile t, int x, int y) {

		Tile target = board[x][y];

		boolean collect = false;
		boolean isChap = t instanceof ChapTile;

		if (isChap) chap = (ChapTile) t;
		
		if(sound.isRunning()) sound.stop();

		if (target instanceof WallTile) {
			throw new IllegalArgumentException("cannot move chap into a wall tile.");
		}

		else if (target instanceof KeyTile) {		
			if (isChap) {
				((ChapTile) t).addKey((KeyTile) target);
				sound.playKeyPickup();
				collect = true;
			}
		}

		else if (target instanceof LockedDoorTile) {
			LockedDoorTile door = (LockedDoorTile) target;
			if (isChap) {
				if (!((ChapTile) t).hasKey(new KeyTile(door.getColor()))) {
					sound.playLockedDoor();
					throw new IllegalStateException("cannot move chap into a locked door tile.");	
				}
				else {
					sound.playOpenDoor();
					((ChapTile) t).removeKey(door.getColor());
					target = new EmptyTile();
				}
			}
			else {
				throw new IllegalStateException("cannot move enemy into a locked door tile.");
			}
		}
		else if (target instanceof TreasureTile) {		
			if (isChap) {
			    int treasureCountOld = checkTreasures();
			    EmptyTile empty = new EmptyTile();
				target = empty;
				board[x][y] = empty;
				int treasureCountNew = checkTreasures();
				assert treasureCountOld == treasureCountNew + 1 : "the current treasure count"
				    + "should decrease by one when a treasure is collected.";
				
				sound.playPickup();
				availableTreasure = treasureCountNew;
				collectedTreasure = totalTreasureCount - availableTreasure;
				
				collect = true;		
			}

		} 
		else if (target instanceof ExitLockTile) {
			if (checkTreasures() > 0) {
				sound.playLockedDoor();
				throw new IllegalStateException("cannot move into a Exit lock Tile");
			}
			else {
				collect = true;
			}		
		}

		else if (target instanceof ExitTile && isChap) {
            this.hasWon = true;
        }
		
		else if (target instanceof ChapTile && !isChap) {
			this.hasLost = true;
			return;
		}
		
		else if (target instanceof EnemyTile && isChap) {
			this.hasLost = true;
			return;
		}
		
		board[getTileX(t)][getTileY(t)] = t.getStandingOn();
		if (!collect) {
		    if (t instanceof ChapTile) sound.playMove();
			t.setStandingOn(target);
		}
		else {
			t.setStandingOn(new EmptyTile());
		}		
		setTile(t, x, y);
	}
	
	/**
	 * return count of tresures remaining
	 * 
	 * @return treasures count
	 */
	public int checkTreasures() {
		int count = 0;
		for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] instanceof TreasureTile) count++;
            }
        }
		return count;
	}

	/**
	 * get the tile at a specified location on the board
	 * 
	 * @param x
	 * @param y
	 * @return Tile
	 */
	public Tile getTileAt(int x, int y) {
		return board[x][y];
	}

	/**
	 * set the tile at a specified location on the board
	 * 
	 * @param t
	 * @param x
	 * @param y
	 */
	public void setTile(Tile t, int x, int y) {
		board[x][y] = t;
	}

	/**
	 * @return current board
	 */
	public Tile[][] getBoard() {
		if (board == null) throw new IllegalStateException("board should not be null");
		return this.board;
	}

	
	/**
	 * get a tiles x position
	 * 
	 * @param t
	 * @return x position
	 */
	public int getTileX(Tile t) {
		return findTile(t)[0];
	}

	/**
	 * get a tiles y position
	 * 
	 * @param t
	 * @return y position
	 */
	public int getTileY(Tile t) {
		return findTile(t)[1];
	}

	private int[] findTile(Tile t) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] != null) {
					if (board[x][y].equals(t)) {
						return new int[] { x, y };
					}
				}
			}
		}
		return null;
	}

	/**
	 * Getter method to return a collection of all the tiles in this maze object
	 * 
	 * @return all tiles
	 */
	public Set<Tile> getAllTiles() {
		Set<Tile> tiles = new HashSet<Tile>();
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				tiles.add(board[x][y]);
			}
		}
		return tiles;
	}
	
	
	/**
	 * return a list of chap and all enemies
	 * 
	 * @return all characters
	 */
	public List<CharacterTile> getCharacters() {
		List<CharacterTile> count = new ArrayList<CharacterTile>();	
		for (Tile t : getAllTiles()) {
			if (t instanceof CharacterTile) count.add((CharacterTile)t);
		}	
		return count;
	}

	@Override
	public String toString() {
		String output = "";		
		for (int x = 0; x < board.length; x++) {
			output += "| ";
			for (int y = 0; y < board[x].length; y++) {
				output += board[x][y].toString() + " | ";
			}
			output += "\n";
		}
		return output;
	}

	
	/**
	 * get the size of this maze's board
	 * 
	 * @return size
	 */
	public int[] getSize() {
		return new int[] {width, height};
	}

	/**
	 * @return board width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return board height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Setter for chap
	 * 
	 * @param c
	 */
	public void setChap(ChapTile c) {
		chap = c;
	}
	
	/**
	 * @return chap
	 */
	public ChapTile getChap() {
		for(Tile t: getAllTiles()) {
			if(t instanceof ChapTile) {return (ChapTile)t;}
		}
		return null;
	}
	
	/**
	 * Checks to see if the game is won
	 * 
	 * @return game status
	 */
	public boolean hasWon() {
		return hasWon;
	}
	
	/**
	 * Checks to see if the game is lost
	 * 
	 * @return game status
	 */
	public boolean hasLost() {
		return hasLost;
	}
	
	/**
	 * getter for this maze's InfoTile (there is never more than one per maze)
	 * 
	 * @return InfoTile
	 */
	public InfoTile getInfo() {
		InfoTile output = null;
		int count = 0;
		for (Tile t : getAllTiles()) {
			if (t instanceof InfoTile) output = (InfoTile)t;
			count++;
		}
		assert count <= 1 : "There should never be more than one InfoTile";
		return output;
	}
	
	/**
	 * Set the current CharacterTile which is being called to move
	 * 
	 * @param t
	 */
	public void setCurrent(CharacterTile t) {
        if(t instanceof EnemyTile) {
            current = (EnemyTile) t;
        } 
        else {
            current = (ChapTile) t;
        }
      
    }
	
	public void update(Observable o, Object arg) {
	    if (mazeStarted) {
	        totalTreasureCount = checkTreasures();
  	        availableTreasure = totalTreasureCount;
  	        collectedTreasure = 0;
  	        assert totalTreasureCount >= 0 : "total treasure count should be non-negative";
  	        mazeStarted = false;
	    }
	    else {
	        availableTreasure = checkTreasures();
	        assert collectedTreasure + availableTreasure == totalTreasureCount
	              : "collected treasure count and available treasure count should"
	              + "add up to the total treasure count";
	    }
	  
        int x = getTileX(current);
        int y = getTileY(current);
                
        Animate animate;
        switch((direction) arg) {
        case UP:
            animate = new Animate(x,y,x,y-1, current);
            animate.animation();
            moveTile(current, x, y-1);
            break;
        case DOWN:
            animate = new Animate(x,y,x,y+1, current);
            animate.animation();
            moveTile(current, x, y+1);
            break;           
        case RIGHT:
            animate = new Animate(x,y,x+1,y, current);
            animate.animation();
            moveTile(current, x+1, y);
            break;           
        case LEFT:
            animate = new Animate(x,y,x-1,y, current);
            animate.animation();
            moveTile(current, x-1, y);
            break;
        default:
            break;         
        }
    }
}
