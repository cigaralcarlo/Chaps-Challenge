package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.app.*;


/**
 * Responsible for the inventory view of the ChapTile
 * 
 * @author Benjamin McEvoy - 300579954
 * */
@SuppressWarnings("serial")
public class InventoryView extends JComponent{
	
	//Fields
	private static final int INVENTORY_WIDTH = 4;
	private static final int INVENTORY_HEIGHT = 2;
	private static final int IMAGE_SIZE = 42;
    private static final int GAP = (85 - IMAGE_SIZE * INVENTORY_HEIGHT)/2; //gap before drawing
	
	private Map<String, Image> imageCache = new HashMap<String, Image>();
	private ChapTile chap;
	private Maze maze;
	
	
	/** Constructor for the inventory view
	 * @param maze 
	 * 
	 * 
	 * @param Maze m - pulls from the maze being currently used.
	 * */
	public InventoryView(Maze maze) {
		this.maze = maze;
		initImage();
		this.setPreferredSize(new Dimension(IMAGE_SIZE * INVENTORY_WIDTH, IMAGE_SIZE * INVENTORY_HEIGHT));
		this.setBackground(getBackground());
	}
	
	/** 
	 * Image initialisation
	 * 
	 * Assigns all image fields to the files from resource folder into the hashmap
	 * 
	 * TODO: Make an loop of the res folder and loop through to put in the map rather
	 * than manually setting it. 
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
	
	/** Gets the Chap
	 * 
	 * */
	private void getChap() {
		Set<Tile> tileSet = maze.getAllTiles();
		for(Tile t : tileSet) {
			if(t instanceof ChapTile) {
				chap = (ChapTile) t;
			}
		}
	}
	
	/** 
	 * Draws a focus area of a 4x2 for the 
	 * inventory view.
	 * 
	 * @param Graphics2D g - the graph it's being drawn on
	 * */
	private void focusArea(Graphics2D g) {
		for(int col = 0; col < INVENTORY_WIDTH; col++) {
			for(int row = 0; row < INVENTORY_HEIGHT; row++) {
				g.drawImage(imageCache.get("freeTile"), col*IMAGE_SIZE + GAP, row*IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, this);
			}
		}
	}
	
	
	/**
	 * Draws the keys whenever a key is in Chap's possession,
	 * or is removed from chap's possession.
	 * 
	 * @param int x - Value it's drawing
	 * @param int y - Value it's drawing
	 * @param Graphics2D g - the graph it's being drawn on
	 * */
	private void drawKey(int x, int y, Graphics2D g) {
		for(int i = 0; i < chap.getKeys().size(); i++) {
			KeyTile key = chap.getKeys().get(i);
			if(i > INVENTORY_WIDTH-1) {
				x = GAP;
				y = IMAGE_SIZE;
			}
			switch(key.getColor()) {
				case "blue":
					g.drawImage(imageCache.get("keyTile_blue"), x, y, IMAGE_SIZE, IMAGE_SIZE, this);
					break;
				case "red":
					g.drawImage(imageCache.get("keyTile_red"), x, y, IMAGE_SIZE, IMAGE_SIZE, this);
					break;
				case "green":
					g.drawImage(imageCache.get("keyTile_green"), x, y, IMAGE_SIZE, IMAGE_SIZE, this);
					break;
				case "yellow":
					g.drawImage(imageCache.get("keyTile_yellow"), x, y, IMAGE_SIZE, IMAGE_SIZE, this);
					break;
				default:
					System.out.println("Something went wrong");
					break;
			}
			x += IMAGE_SIZE;
		}
	}
	
	
	/**
	 * Overrides the paintComponent from the Javax,
	 * Responsible for drawing the inventory view area in the Application 
	 *
	 * 
	 * @param Graphics g - Graphics pane being draw on
	 * */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = GAP;
		int y = 0;
    	Graphics2D graph2d = (Graphics2D) g;
    	focusArea(graph2d);
    	getChap();
    	drawKey(x,y,graph2d);
	}
	
}
