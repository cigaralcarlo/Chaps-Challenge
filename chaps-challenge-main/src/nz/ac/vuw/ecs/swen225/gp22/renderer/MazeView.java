package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.awt.*;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.imageio.spi.ImageReaderWriterSpi;
import javax.swing.*;
import java.io.*;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;

/** 
 * Maze View class to update and render the maze whenever
 * the character moves or does an action, responsible for 
 * 
 * @author Benjamin McEvoy - 300579954
 * */
@SuppressWarnings("serial")
public class MazeView extends JComponent{
	
	// Fields
	private Map<String, Image> imageCache = new HashMap<String, Image>();
	
	private int vRange = 9; //range of vision
	private int chapX, chapY, indentSize;
	private int imageSize = 42;
	private int cameraSize = vRange * imageSize; // 9 * 42
	
	private Maze maze; 
	private Tile[][] chapView;
	private Set<Tile> tileSet;
	
	/**
	 * Constructor of MazeView
	 * 
	 * Receives the maze in use, and initializes the board to draw and play the sound.
	 * 
	 * @param Maze m 
	 * */
	public MazeView(Maze m){
		initialize();
		this.maze = m;
		this.tileSet = m.getAllTiles();
		initImage();
	}
	
	/**
	 *  Initializes the indent and image size and
	 *  any other field or variables that may need
	 *  initialization.
	 * 
	 * */
	private void initialize() {
		indentSize = 168;
		setPreferredSize(new Dimension(cameraSize, cameraSize));
	}
	
	/** Updates the board
	 * */
	private void updateMaze() {
		chapView = maze.getBoard();
	}

	/** 
	 * Image initialisation
	 * 
	 * Assigns all image fields to the files from resource folder into the hashmap
	 * 
	 * @return hash
	 */
	private void initImage(){
		try {
			File folder = new File("res/graphics");
			File[] imageList = folder.listFiles();
			for(int i = 0; i< imageList.length; i++){
				if(imageList[i].isFile()){
				    String imageName = imageList[i].getName().substring(0 , imageList[i].getName().length()-4);
				    imageCache.put(imageName, ImageIO.read(imageList[i]));
				} 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds chap within the tile set and
	 * updates the X and Y at the tile.
	 * 
	 * */
	private void findChap() {
		for (Tile t: tileSet) {
			if (t instanceof ChapTile) {
				chapX = maze.getTileX(t);
				chapY = maze.getTileY(t);
			}
		}
	}
	
	/**
	 *  Get the maze/board/pane
	 * @return maze
	 * 
	 * */
	public Maze getMaze() {
		return maze;
	}
   
	
    /**
     *  Draws all tiles in a focus area
     *  @param Tile[][] board - Board array/camera view
     *  @param Graphics2D g - Graphics Pane
     * */    
    private void focusArea(Tile[][] board, Graphics2D g) {
    	for(int col = -5; col <5; col++){
    	    for(int row = -5; row < 5; row++) {
    	        if(chapX + row >= 0 && chapY + col >=0 && chapX + row < board.length && chapY + col <board[0].length) {
    	        	if(board[chapX + row][chapY + col] != null) {
    	        	g.drawImage(imageCache.get(board[chapX+row][chapY+col].getFileName()), indentSize + row*imageSize, indentSize +col* imageSize, this);
    	        	}else {
    	        		g.drawImage(imageCache.get("freeTile"), indentSize + row*imageSize, indentSize +col* imageSize, this);
    	        	}
    	        }else {
    	        		g.drawImage(imageCache.get("freeTile"), indentSize + row*imageSize, indentSize +col* imageSize, this);
    	        	}
    	    }
    	}
    }
    
    /**
	 * Overrides the paintComponent from the Javax,
	 * Responsible for drawing the maze view area in the Application 
	 *
	 * 
	 * @param Graphics g - Graphics pane being draw on
	 * */
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	findChap();
    	updateMaze();
    	Graphics2D graph2d = (Graphics2D) g;
    	focusArea(chapView, graph2d);
    }
}
