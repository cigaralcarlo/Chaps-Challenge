package test.nz.ac.vuw.ecs.swen225.gp22.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import nz.ac.vuw.ecs.swen225.gp22.domain.ChapTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.EmptyTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.EnemyTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.InfoTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.KeyTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.LockedDoorTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.TreasureTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.WallTile;

class DomainTesting {
	
	@SuppressWarnings("deprecation")
    @Rule
	public final ExpectedException exception = ExpectedException.none();

	/*
	@Test
	public void test() {
		
		
		
		fail("Not yet implemented");
	}
	*/
	@Test
	public void testEquals1() {		
		Maze maze = new Maze(2, 2, 1);
		fillMaze(maze);
		if (!maze.equals(maze)) {
			fail("maze should equal itself");
		}
	}
	
	
	@Test
	public void testChapMovement() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		maze.setTile(chap, 0, 0);
		maze.setChap(chap);
		chap.setStandingOn(new EmptyTile());
		//maze.setTile(new TreasureTile(), 0, 1);
		maze.moveTile(chap, 0, 1);
		maze.moveTile(chap, 1, 1);
		
		
		if (!classesEqual(maze.getTileAt(0, 1),new EmptyTile())) {
			fail("Chap should be at location 1,1");
		}
		
	}
	
	@Test
	public void testKeyTile() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		maze.setTile(chap, 0, 0);
		maze.setTile(new KeyTile("red"), 0, 1);
		maze.moveTile(chap, 0, 1);
		maze.moveTile(chap, 1, 1);
		
		if (!classesEqual(maze.getTileAt(0, 1),new EmptyTile())) {
			fail("Chap should have picked up key");
		}
	}
	
	@Test
	public void testTreasureTile() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		maze.setTile(chap, 0, 0);
		maze.setTile(new TreasureTile(), 0, 1);
		maze.moveTile(chap, 0, 1);
		maze.moveTile(chap, 1, 1);
		
		if (!(maze.getTileAt(0, 1).getClass().equals(new EmptyTile().getClass()))) {
			fail("Chap should have picked up treasure");
		}
	}
	
	
	
	@Test
	public void testWallTile() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		maze.setTile(chap, 0, 0);
		maze.setTile(new WallTile(), 1, 0);

		try {
			maze.moveTile(chap, 1, 0);
			
			fail("IllegalArgumentException should be thrown when chap moved into wall");
		} 
		catch (IllegalArgumentException excpectedException) {}
	}
	
	
	
	@Test
	public void testLockedDoor() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		KeyTile key = new KeyTile("red");
		maze.setTile(chap, 0, 0);
		maze.setTile(new LockedDoorTile("red"), 1, 2);
		maze.setTile(key, 1, 0);
		
		maze.moveTile(chap, 1, 0);
		maze.moveTile(chap, 1, 1);
		maze.moveTile(chap, 1, 2);
		
		
		if (!(maze.getBoard()[1][2].equals(chap))) {
			fail("chap should be standing where the locked door was previously");
		}
	}
	
	@Test
    public void testExitLock1() {
        Maze maze = new Maze(3, 3, 1);
        fillMaze(maze);
        ChapTile chap = new ChapTile();
        maze.setTile(chap, 0, 0);
        maze.setTile(new LockedDoorTile("red"), 1, 0);

        try {
            maze.moveTile(chap, 1, 0);
            
            fail("IllegalStateException should be thrown when chap moved into ExitLockTile without key");
        } 
        catch (IllegalStateException excpectedException) {}
    }
	
	@Test
	public void testExitLock2() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		
		maze.setTile(chap, 0, 0);
		maze.setTile(new ExitLockTile(), 2, 2);
		maze.setTile(new TreasureTile(), 1, 0);
		
		maze.moveTile(chap, 1, 0);
		maze.moveTile(chap, 1, 1);
		maze.moveTile(chap, 1, 2);
		maze.moveTile(chap, 2, 2);
		
		if (!(maze.getBoard()[2][2].equals(chap))) {
			fail("chap should be standing where the ExitLock was previously");
		}
		
	}
	
	@Test
	public void testGameState1() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		
		maze.setTile(chap, 0, 0);
		maze.setTile(new ExitTile(), 2, 2);
		
		maze.moveTile(chap, 1, 0);
		maze.moveTile(chap, 1, 1);
		maze.moveTile(chap, 1, 2);
		maze.moveTile(chap, 2, 2);
		
		if(!maze.hasWon()) {
			fail("game should be won");
		}
		
		
		
	}
	@Test
	public void testInfoTile1() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		InfoTile info = new InfoTile("test");
		maze.setTile(chap, 0, 0);
		maze.setTile(info, 2, 2);
		
		maze.moveTile(chap, 1, 0);
		maze.moveTile(chap, 1, 1);
		maze.moveTile(chap, 1, 2);
		maze.moveTile(chap, 2, 2);
		
		if(!info.isDiplaying()) {
			fail("InfoTile should be displaying its text");
		}		
	}
	
	@Test
	public void testInfoTile2() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		InfoTile info = new InfoTile("test");
		maze.setTile(chap, 0, 0);
		maze.setTile(info, 1, 1);
		
		maze.moveTile(chap, 1, 0);
		maze.moveTile(chap, 1, 1);
		maze.moveTile(chap, 1, 2);
		maze.moveTile(chap, 2, 2);
		
		if(info.isDiplaying()) {
			fail("InfoTile should no longer displaying its text");
		}
	}
	@Test
	public void testMazeUpdate() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		maze.setChap(chap);
		maze.setTile(chap, 1, 1);
		try {
    		maze.update(null, Maze.direction.UP);
    		maze.update(null, Maze.direction.DOWN);
    		maze.update(null, Maze.direction.LEFT);
    		maze.update(null, Maze.direction.RIGHT);
    		fail("NullPointerException should be thrown as update requires values stored in other domains");
        } 
        catch (NullPointerException excpectedException) {}
	}
	
	
	
	@Test
	public void testEnemyMovement1() {
		Maze maze = new Maze(3, 3, 1);
		fillMaze(maze);
		ChapTile chap = new ChapTile();
		ArrayList<direction> directions = new ArrayList<direction>();
		directions.add(direction.RIGHT);
		directions.add(direction.DOWN);
		directions.add(direction.LEFT);
		directions.add(direction.UP);
		EnemyTile enemy = new EnemyTile("enemy1", directions);
		maze.setTile(enemy, 0, 0);
		maze.setTile(new KeyTile("red"), 0, 1);
		maze.moveTile(enemy, 0, 1);
		maze.moveTile(enemy, 1, 1);

		
		
		if (!(maze.getTileAt(1, 1).getClass().equals(new EnemyTile("", directions).getClass()))) {
			fail("enemy tile should be at position 1, 1");
		}
		
		
	}
	
	@Test
    public void testChapMove() {
        Maze maze = new Maze(3, 3, 1);
        fillMaze(maze);
        ChapTile chap = new ChapTile();
        maze.setChap(chap);
        maze.setTile(chap, 1, 1);
        maze.setTile(chap, 1, 1);
        chap.addNextMove(direction.UP);
        chap.addNextMove(direction.DOWN);
        chap.addNextMove(direction.LEFT);
        chap.addNextMove(direction.RIGHT);
        chap.move(maze);
        chap.move(maze);
        chap.move(maze);
        chap.move(maze);
    }
    
    @Test
    public void testToString() {
        Maze maze = new Maze(10, 10, 1);
        fillMaze(maze);
        ChapTile chap = new ChapTile();
        maze.setChap(chap);
        maze.setTile(chap, 1, 1);
        maze.setTile(new WallTile(), 0, 0);
        maze.setTile(new InfoTile("text"), 1, 0);
        maze.setTile(new KeyTile("red"), 2, 0);
        maze.setTile(new LockedDoorTile("red"), 3, 0);
        maze.setTile(new TreasureTile(), 8, 0);
        maze.setTile(new ExitTile(), 4, 0);
        maze.setTile(new ExitLockTile(), 5, 0);
        maze.setTile(new EnemyTile("enemy", null), 6, 0);
       
        maze.toString();
        
        for (Tile t : maze.getAllTiles()) {
            t.toString();
            t.getFileName();
        }
    }
    
    @Test
    public void testGetters() {
      Maze maze = new Maze(3, 3, 1);
      ChapTile chap = new ChapTile();
      maze.setCurrent(chap);
      maze.getInfo();
      maze.getChap();
      maze.getLevel();
      maze.getSize();
      maze.getCharacters();
    }
    
    
	
	private void fillMaze(Maze m) {
		for (int x = 0; x < m.getWidth(); x++) {
			for (int y = 0; y < m.getHeight(); y++) {
				m.setTile(new EmptyTile(), x, y);
			}
		}
	}
	
	private boolean classesEqual(Tile first, Tile second) {
		return first.getClass().equals(second.getClass());
	}

}
