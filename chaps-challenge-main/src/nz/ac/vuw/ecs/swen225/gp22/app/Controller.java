package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

/**
 * Controller class used to listen to user inputs
 * 
 * @author Carlo Cigaral - 300572686
 *
 */
public class Controller implements KeyListener{
	
	private final Set<Integer> pressedKeys = new HashSet<>();
	
	//Initializes key codes for user inputs
	static boolean paused = false;
	static int up = KeyEvent.VK_UP;
	static int left = KeyEvent.VK_LEFT;
	static int down = KeyEvent.VK_DOWN;
	static int right = KeyEvent.VK_RIGHT;
	static int ctr = KeyEvent.VK_CONTROL;
	static int one = KeyEvent.VK_1;
	static int two = KeyEvent.VK_2;
	static int x = KeyEvent.VK_X;
	static int s = KeyEvent.VK_S;
	static int r = KeyEvent.VK_R;
	static int space = KeyEvent.VK_SPACE;
	static int esc = KeyEvent.VK_ESCAPE;

	Maze maze;
	ChapTile chap;
	
	//Field to keep track of the latest saved move
	private static Maze.direction savedMove = Maze.direction.NULL;

	/**
	 * Getter for Set of keys the user is currently pressing down
	 * 
	 * @return Returns pressedKeys as an ArrayList
	 */
	public ArrayList<Integer> getPressedKeys(){
		return new ArrayList<Integer>(pressedKeys);
	}

	/**
	 * Getter for the latest saved move
	 * 
	 * @return savedMove Maze.direction enum that is the latest saved move
	 */
	public Maze.direction getSavedMove(){
	  return savedMove;
	}
	
	/**
	 * Setter for the latest saved move
	 * 
	 * @param move Maze.direction enum for the latest saved move
	 */
	public void setSavedMove(Maze.direction move) {
	  Controller.savedMove = move;
	}
	
	/**
	 * Constructor for Controller
	 * 
	 * @param maze Current maze with full tile set, needed to access the Chap tile
	 */
	public Controller(Maze maze) {
		this.maze = maze;
		chap = maze.getChap();
	}

	/**
	 * Listens for when a key is pressed
	 * 
	 * @param e Key Event detected
	 */
	public void keyPressed(KeyEvent e) { 
		
		pressedKeys.add(e.getExtendedKeyCode());
		
		if(pressedKeys.contains(ctr)) {
			
			//User enters Ctr + 1 to reload a new Level 1
			if(e.getExtendedKeyCode() == one) {
				new Game(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/Levels/level1.xml"));
			} 
			
			//User enters Ctr + 2 to reload a new Level 2
			else if(e.getExtendedKeyCode() == two) {
				new Game(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/Levels/level2.xml"));
			} 
			
			//User enters Ctr + X to exit without saving
			else if(e.getExtendedKeyCode() == x) {
				System.exit(0);
			} 
			
			//User enters Ctr + S to exit and save current game
			else if(e.getExtendedKeyCode() == s) {
				
				//File chooser to allow user to save game into a new file/overwrite existing file
				JFileChooser chooser = new JFileChooser(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/SavedGame"));
				int j = chooser.showSaveDialog(null);
				if(j == JFileChooser.APPROVE_OPTION) {
					try {
						File f = chooser.getSelectedFile();
						Recorder.SaveGame(maze, f);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);
			} 
			
			//User enters Ctr + R to resume a saved game
			else if(e.getExtendedKeyCode() == r) {
				
				//File chooser to allow user to select an existing file to load
				JFileChooser chooser = new JFileChooser(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/SavedGame"));
				int j = chooser.showOpenDialog(null);
				if(j == JFileChooser.APPROVE_OPTION) {
					try {
						File f = chooser.getSelectedFile();
						new Game(Recorder.LoadSave(f));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			
		} else {
			if(!paused) {
				maze.setCurrent(chap);
				
				//User moves up
				if(e.getExtendedKeyCode() == up) {
					maze.update(null, Maze.direction.UP);
					chap.addPreviousMove(Maze.direction.UP);
					savedMove = Maze.direction.UP;
				}
				
				//User moves left
				else if(e.getExtendedKeyCode() == left) {
					maze.update(null, Maze.direction.LEFT);
					chap.addPreviousMove(Maze.direction.LEFT);
					savedMove = Maze.direction.LEFT;
				}
				
				//User moves down
				else if(e.getExtendedKeyCode() == down) {
					maze.update(null, Maze.direction.DOWN);
					chap.addPreviousMove(Maze.direction.DOWN);
					savedMove = Maze.direction.DOWN;
				}
				
				//User moves right
				else if(e.getExtendedKeyCode() == right) {
					maze.update(null, Maze.direction.RIGHT);
					chap.addPreviousMove(Maze.direction.RIGHT);
					savedMove = Maze.direction.RIGHT;
				}
				
				//User pauses
				else if (e.getExtendedKeyCode() == space) {
					paused = true;
				}
				
			}
			
			//User unpauses
			else if(e.getExtendedKeyCode() == esc && paused) {
				paused = false;
			}

		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		pressedKeys.remove(e.getExtendedKeyCode());
	}
}
