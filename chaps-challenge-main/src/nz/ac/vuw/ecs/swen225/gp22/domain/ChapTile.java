package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.*;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for chap
 *
 */

public class ChapTile extends CharacterTile{
	
	private ArrayList<KeyTile> keys;
	//private boolean alive;
	private String file = "chap";
	
	private Stack<Maze.direction> previousMoves = new Stack<Maze.direction>();
	private Stack<Maze.direction> nextMoves = new Stack<Maze.direction>();

	/**
	 * Constructor initializes the keys arraylist
	 */
	public ChapTile() {
		super("chap");
		keys = new ArrayList<KeyTile>();
		//alive = true;
	}
	
	/**
	 * add a key to chap
	 * 
	 * @param k
	 */
	public void addKey(KeyTile k) {
		keys.add(k);
	}
	
	/**
	 * remove a key from chap by its Object
	 * 
	 * @param k
	 */
	public void removeKey(KeyTile k) {
		keys.remove(k);
	}
	
	/**
	 * remove a key from chap by its String value
	 * 
	 * @param k
	 */
	public void removeKey(String k) {
		KeyTile remove = null;
		for (KeyTile key : keys) {
	    	if (key.getColor().equals(k)) remove = key;
	    }
		keys.remove(remove);
	}
	
	/**
	 * check if chap has a specified key
	 * 
	 * @param k
	 * @return if chap has said key
	 */
	public boolean hasKey(KeyTile k) {
	    for (KeyTile key : keys) {
	    	if (key.getColor().equals(k.getColor())) return true;
	    }
	    return false;
	}
	
	/**
	 * get a list of all keys
	 * 
	 * @return keys
	 */
	public ArrayList<KeyTile> getKeys() {
		return keys;
	}
	
	/**
	 * add a move to the previous moves stack
	 * 
	 * @param d
	 */
	public void addPreviousMove(Maze.direction d) {
		previousMoves.push(d);
	}
	
	/**
	 * add a move to the next moves stack
	 * 
	 * @param d
	 */
	public void addNextMove(Maze.direction d) {
		nextMoves.push(d);
	}
	
	/**
	 * get the previous moves stack
	 * 
	 * @return previousMoves
	 */
	public Stack<Maze.direction> getPreviousMoves() {
		return previousMoves;
	}
	
	/**
	 * get the next moves stack
	 * 
	 * @return nextMoves
	 */
	public Stack<Maze.direction> getNextMoves() {
		return nextMoves;
	}

	/**
	 * change the file String variable which stores the filename of what picture of chap should be displayed
	 */
	public void getFLeft() {
		file = "chap_left";
	}
	
	/**
	 * 
	 */
	public void getFRight() {
		file = "chap_right";
	}
	
	/**
	 * 
	 */
	public void getFUp() {
		file = "chap_up";
	}
	
	/**
	 * 
	 */
	public void getFDown() {
		file = "chap_down";
	}

	/**
	 * 
	 */
	public void getFIdle() {
		file = "chap";
	}
	
	
	@Override
	public String toString() {
		return "Chap";
	}

	@Override
	public String getFileName() {
		return file;
	}

	@Override
    public void move(Maze m) {
        Maze.direction d = nextMoves.pop();
        m.setCurrent(this);
        switch(d) {
            case UP:
                m.update(null, Maze.direction.UP);
                break;
            case LEFT:
                m.update(null, Maze.direction.LEFT);
                break;
            case DOWN:
                m.update(null, Maze.direction.DOWN);
                break;
            case RIGHT:
                m.update(null, Maze.direction.RIGHT);
                break;
            default:
                break;
        }
        
    }
	

}
