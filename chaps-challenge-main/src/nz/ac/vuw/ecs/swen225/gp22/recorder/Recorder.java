package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze.direction;
import nz.ac.vuw.ecs.swen225.gp22.persistency.XMLLoader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Recorder class used for saving current game, loading and running
 * through past saved games.
 *
 * @author Ethan Windle
 */
public class Recorder {
  /**
   * auto is used to inform game that it should be automatically running through
   * the loaded game. playbackSpeed is used to determine how fat it should
   * automatically play through a loaded game.
   */
  public static boolean auto = false;
  public static int playbackSpeed = 1;

  /**
   * Save the current game in an xml file that the player chooses/create.
   *
   * @param maze the current games maze.
   * @param file the file the game will be saved to.
   */
  public static void SaveGame(Maze maze, File file) {
    //error handling can't save nothing
    if (maze == null) {
      System.out.println("Nothing to be saved");
      return;
    }
    
    //catches errors
    try {
      // create root node
      Element root = new Element("save");
      root.setAttribute("level", maze.getLevel() + "");

      // crate each character element and add to root node
      Element character = new Element("character");
      character.setAttribute("name", "chap");
      
      //create string of saved moves
      String str = "";
      for (direction d : maze.getChap().getPreviousMoves()) {
        //add separator if not first move added
        if (!str.equals("")) {
          str = ", " + str;
        }
        
        //add current move to string
        switch (d) {
          case UP:
            str = "up" + str;
            break;
          case LEFT:
            str = "left" + str;
            break;
          case DOWN:
            str = "down" + str;
            break;
          case RIGHT:
            str = "right" + str;
            break;
          case NULL:
            str = "null" + str;
            break;
        }
      }
      
      //Write the xml document
      Element moves = new Element("moves");
      moves.addContent(str);
      character.addContent(moves);
      root.addContent(character);

      // Save as doc
      Document doc = new Document();
      doc.setRootElement(root);
      
      //output as xml file
      XMLOutputter xmlOutputter = new XMLOutputter();
      xmlOutputter.setFormat(Format.getPrettyFormat());
      xmlOutputter.output(doc, new FileOutputStream(file));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a saved game chosen by the player.
   *
   * @param file the saved game the player chooses to load.
   * @return returns the maze of the saved level.
   * @throws JDOMException handles the xml reading errors.
   * @throws IOException handles any file errors.
   */
  public static Maze LoadSave(File file) throws JDOMException, IOException {
    // makes sure auto-play starts off
    auto = false;
    
    // load XML file document
    SAXBuilder sax = new SAXBuilder();
    Document doc = sax.build(file);

    // create level stored in xml document
    Element root = doc.getRootElement();
    String level = root.getAttributeValue("level");
    XMLLoader levelLoader = new XMLLoader();
    levelLoader
        .loadFile(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/Levels/level" + level + ".xml"));

    // update characters future movement
    Maze maze = levelLoader.getMaze();
    Element element = root.getChild("character");

    // find the right character
    String[] moves = element.getChild("moves").getText().split(", ");
    
    // load chap in maze
    ChapTile chap = maze.getChap();
    
    // push moves on to characters next moves stack
    for (String str : moves) {
      switch (str) {
        case "up":
          chap.addNextMove(direction.UP);
          break;
        case "left":
          chap.addNextMove(direction.LEFT);
          break;
        case "down":
          chap.addNextMove(direction.DOWN);
          break;
        case "right":
          chap.addNextMove(direction.RIGHT);
          break;
        case "null":
          chap.addNextMove(direction.NULL);
          break;
      }
    }
    
    // return the new maze
    return maze;
  }
  
  /**
   * Runs all characters next move if chap still has some else turn off auto replay.
   *
   * @param m passes through the current maze.
   */
  public static void Next(Maze m) {
    // moves all characters if chap still has saved moves left otherwise turn off auto-play.
    if (!m.getChap().getNextMoves().isEmpty()) {
      m.getCharacters().stream().forEach(i -> i.move(m));
    } else {
      auto = false;
    }
  }

  /**
   * Makes sure auto play is off and manual run the next move.
   *
   * @param m passes through the current maze.
   */
  public static void nextMove(Maze m) {
    auto = false;
    Next(m);
  }

  /**
   * Inverse the auto boolean allowing the player to pause/play the auto play of their loaded game.
   */
  public static void AutoPlay() {
    auto = !auto;
  }
}
