package nz.ac.vuw.ecs.swen225.gp22.persistency;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.direction;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XMLLoader {
	
	ChapTile chap;
	Maze maze; //2D array of Map
	
	public Maze getMaze() {
		return this.maze;
	}
	
	
	public void loadFile(File file) {
		try {
			
			SAXBuilder sax = new SAXBuilder();
			Document doc = sax.build(file);
			Element rootNode = doc.getRootElement();
			parseLevel(rootNode); //parse the level, construct new maze
			parseBoard(rootNode);
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	private void parseLevel(Element e) {
		int level = Integer.parseInt(e.getAttributeValue("level"));
		int x = Integer.parseInt(e.getAttributeValue("x"));
		int y = Integer.parseInt(e.getAttributeValue("y"));
		this.maze = new Maze(x, y, level);
	}
	
	private void parseChap(Element e) {
        int x = Integer.parseInt(e.getChild("location").getAttributeValue("xpos"));
        int y = Integer.parseInt(e.getChild("location").getAttributeValue("ypos"));
        this.chap = new ChapTile();
        maze.setTile(chap, x, y);
    }
	
	private void parseBoard(Element e) {
		List<Element> tiles = e.getChildren();
		for (Element f:tiles) {
			//String tile = f.getChild("tile").getAttributeValue("class");
			//List<Element> currTiles = f.getChildren();
			if(e.getChildren("tile").contains(f)) {
				Element currTile = f.getChild("location");
				int x = Integer.valueOf(currTile.getAttributeValue("xpos"));
				int y = Integer.valueOf(currTile.getAttributeValue("ypos"));
				if (f.getAttributeValue("class").equals("wall")) {
					maze.setTile(new WallTile(), x, y);
				} else if(f.getAttributeValue("class").equals("free")) {
					maze.setTile(new EmptyTile(), x, y);
				} else if (f.getAttributeValue("class").equals("door")) {
					String colour = f.getAttributeValue("colour");
					maze.setTile(new LockedDoorTile(colour), x, y);
				} else if (f.getAttributeValue("class").equals("key")) {
					String colour = f.getAttributeValue("colour");
					maze.setTile(new KeyTile(colour), x, y);
				} else if (f.getAttributeValue("class").equals("treasure")) {
					maze.setTile(new TreasureTile(), x, y);
				} else if (f.getAttributeValue("class").equals("exitDoor")) {
					maze.setTile(new ExitTile(), x, y);
				} else if (f.getAttributeValue("class").equals("info")) {
					String info = f.getChild("info").getText();
					maze.setTile(new InfoTile(info), x, y);
				} else if (f.getAttributeValue("class").equals("exitLock")) {
					maze.setTile(new ExitLockTile(), x, y);
				}
			} else if (e.getChildren("character").contains(f)) {
				Element currTile = f.getChild("location");
				int x = Integer.valueOf(currTile.getAttributeValue("xpos"));
				int y = Integer.valueOf(currTile.getAttributeValue("ypos"));
				if (f.getAttributeValue("name").equals("enemy")) {
					String name = f.getAttributeValue("name");
					String movesString = f.getChild("moves").getText();
					List<direction> directions = stringToDirections(movesString);
					maze.setTile(new EnemyTile(name, directions), x, y);
				}
				else {
	                parseChap(f);
	            }
			}
		}
	}	
	
	private List<direction> stringToDirections(String movesString){
		List<String> moves = Arrays.asList(movesString.split(", "));
		List<direction> directions = new ArrayList<>();
		for (String m:moves) {
			if (m.equals("up")) {directions.add(Maze.direction.UP);}
			else if (m.equals("down")) {directions.add(Maze.direction.DOWN);}
			else if (m.equals("left")) {directions.add(Maze.direction.LEFT);}
			else if (m.equals("right")) {directions.add(Maze.direction.RIGHT);}
		}
		return directions;
	}
}
