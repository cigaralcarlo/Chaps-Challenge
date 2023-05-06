package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp22.app.Game;
import nz.ac.vuw.ecs.swen225.gp22.domain.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.TreasureTile;

/**
 * @author Vedaanth Kannan 300482816
 *
 */

public class FuzzVedaanth {
	static final Random r = new Random();
	private static Game game;
	private static Tile exit;
	private static int level = 1;
	private static String s;
	private final List<Integer> direction = List.of(KeyEvent.VK_UP,
            KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN);
	 private final Map<Integer, Integer> directionsAndOpp =
	            Map.of(KeyEvent.VK_UP, KeyEvent.VK_DOWN,
	                    KeyEvent.VK_DOWN, KeyEvent.VK_UP,
	                    KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
	                    KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
	 
	 private List<Integer> movesGenerator(){// Move generator method that generates 10,000 random moves for patrick to do
		 int lastMove = -1;
		 List<Integer> actions = new ArrayList<>();
		 for (int i = 0; i < 10000; i++) {
			 while (true) {
	                int random = r.nextInt(direction.size());
	                int move = direction.get(random);
	                if (lastMove == -1 || move != directionsAndOpp.get(lastMove)) {//checking if the next move is not the last move
	                    actions.add(move);
	                    lastMove = move;
	                    break;
	                }
			 }
		 }
		 return actions;
	 }
	 private void test() {
		 Maze m = new Maze(10,10,level); //starts new level 1 game
		 for(Tile t: m.getAllTiles()) {
			 if(t instanceof ExitLockTile) {//checking for instance of exit tile to get its location
				 exit = m.getTileAt(m.getTileX(t), m.getTileY(t)); //setting the exit tile
			 }
		 }
	        try {
	            Robot robot = new Robot();//using robot to automate chap/patrick
	            List<Integer> generatedMoves = movesGenerator();
	            for (int key : generatedMoves) {
	                SwingUtilities.invokeLater(new Runnable() {
	                    public void run() {
	                        robot.keyPress(key);//pressing key/input for robot
	                    }
	                });
	                try {
	                    Thread.sleep(10);
	                    SwingUtilities.invokeLater(new Runnable() {
	                        public void run() {
	                        	s = "";
	                            robot.keyRelease(key);//releasing key/input for robot
	                            if((m.getTileX(m.getChap()) == m.getTileX(exit))&&(m.getTileY(m.getChap()) == m.getTileY(exit))) {//checking if chap is on the exit and if he is, is he on the exit tile. 
	                            	for(Tile t: m.getAllTiles()) {
                            			if(t instanceof TreasureTile) {//if both cases are true, checking if there are any treasures left in the maze and if there is, breaking the loop and going back to the game as normal
                            				break;
                            				
                            			}
                            		level++;//if not adding to the level
                            		s = "Chap is on the exit and has eaten all burgers";
									/*
									 * try { Thread.sleep(10000); } catch (InterruptedException e) { // TODO
									 * Auto-generated catch block e.printStackTrace(); }
									 */
                            		assert(s.equalsIgnoreCase("Chap is on the exit and has eaten all burgers")); //asserting that level has passed and moving to the next test
	                            }
	                        }
	                        }
	                        });
	                } catch (InterruptedException e) {
	                }
	            }

	        } catch (AWTException e) {
	            e.printStackTrace();
	        }
	    }
	 /**
	 * level 1 test method
	 */
	public void level1Test() {
	        try {
	            SwingUtilities.invokeLater(() -> {game = new Game(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/Levels/1.xml"));});
	        } catch (Error e) {
	        }
	        test();
	    }
	 /**
	 * level 2 test method
	 */
	public void level2Test() {
		 try {
	            SwingUtilities.invokeLater(() -> {game = new Game(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/Levels/2.xml"));});
	        } catch (Error e) {
	        }
	        test();
	 }
	 
	    /**
	     * Actual fuzz test
	     */
	    @Test
	    public void timedTest() {
	        try {
	            assertTimeoutPreemptively(Duration.ofSeconds(60), () -> level1Test());//AssertTimeout didn't stop the test at 60 seconds so used AssertTimeoutPreemptively to force quit the test to preemptively abort the test
	            assertTimeoutPreemptively(Duration.ofSeconds(60), () -> level2Test());
	        } catch (Exception e) {

	        }
	    
	    }
}

