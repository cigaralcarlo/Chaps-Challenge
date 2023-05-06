package nz.ac.vuw.ecs.swen225.gp22.domain;


import java.util.*;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.direction;

/**
 * @author roddicadam - 300580773
 * 
 * Tile sublcass for Enemies
 *
 */
public class EnemyTile extends CharacterTile{
	private List<direction> pattern = new ArrayList<direction>();
	int patternLocation = 0;
	private String file = "enemy_left";
	
	/**
	 * constructor sets the enemies name and pattern they should follow
	 * 
	 * @param name
	 * @param pattern
	 */
	public EnemyTile(String name, List<direction> pattern) {
		super(name);
		this.pattern = pattern;
	}
	
	public void move(Maze maze) {
        maze.setCurrent(this);
		switch(pattern.get(patternLocation)){
			case UP:
				maze.update(null, Maze.direction.UP);
				break;
			case DOWN:
				maze.update(null, Maze.direction.DOWN);
				break;
			case LEFT:
				maze.update(null, Maze.direction.LEFT);
				break;
			case RIGHT:
				maze.update(null, Maze.direction.RIGHT);
				break;
			
			default:
				break;
		}
		patternLocation++;
		if(patternLocation==pattern.size()) patternLocation = 0;
	}
	
	/**
	 * change the file String variable which stores the filename of what picture of this enemy should be displayed
	 */
	public void getFLeft() {
		file = "enemy_left";
	}

	/**
	 * 
	 */
	public void getFRight() {
		file = "enemy_right";
	}


	@Override
	public String toString() {
		return "Enemy";
	}

	@Override
	public String getFileName() {
		return file;
	}

}
