package nz.ac.vuw.ecs.swen225.gp22.renderer;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.*;


/**
 * Responsible for the audio files within the game
 * 
 * @author Benjamin McEvoy - 300579954
 * */
public class Sound {

	//Fields
	
	private String amb, move, lockedD, openD, keyPick, pickUp, chapStung, win;
	private Clip clip1, clip2;
	

	/**
	 *  Sound Constructor
	 * 
	 * 	Fetches the current directory and iterates through the audio files in the resource
	 *  directory, and places the filename and s
	 */
	public Sound() {
		initialize();
	}

	/**
	 * Initialize function
	 * 
	 * Initializes fields and does a wipe of any
	 * remaining data when calling a new sound.
	 * 
	 */
	private void initialize() {
		amb = "res/audio/background3.wav";
		move = "res/audio/soundMove2.wav";
		lockedD = "res/audio/soundLocked.wav";
		openD = "res/audio/soundOpen.wav";
		keyPick = "res/audio/soundKeyPickup.wav";
	    pickUp = "res/audio/soundPickup.wav";
		chapStung = "res/audio/soundStung.wav";
		win = "res/audio/soundWin.wav";
	}
	
	
	/** Plays the ambient background sound
	 * 
	 *  Calls the playBackground function instead of play as it must be looped
	 * */
	public void playAmbient() {
		try {
			playBackground(amb);
		} catch (IOException | UnsupportedAudioFileException|LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/** Plays the locked door sound
	 * 
	 *  Calls the play function
	 * */
	public void playLockedDoor() {
			play(lockedD);
	}
	
	/** Plays the openDoor sound
	 * 
	 *  Calls the play function
	 * */
	public void playOpenDoor() {
			play(openD);
	}
	
	/** Plays the move sound
	 * 
	 *  Calls the play function
	 * */
	public void playMove() {
		play(move);
	}
	
	
	/** Plays the chap stung sound
	 * 
	 *  Calls the play function
	 * */
	public void playStung() {
		play(chapStung);
	}
	
	/** Plays the pickup sound
	 * 
	 *  Calls the play function
	 * */
	public void playKeyPickup() {
		play(keyPick);
	}
	
	/** Plays the locked door sound
     * 
     *  Calls the play function
     * */
    public void playPickup() {
            play(pickUp);
    }
	
	/** Plays the move sound
	 * 
	 *  Calls the play function
	 * */
	public void playWin() {
		play(win);
	}

	/**
	 * Play function
	 * 
	 * Responsible for playing the sound of a given file
	 * 
	 * @param soundName
	 */
	private void play(String soundName) {
		//loads the sound file and plays it
		try {
			File f = new File("./" + soundName);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	        clip1 = AudioSystem.getClip();
	        clip1.open(audioInputStream);
	        clip1.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Play Background function
	 * 
	 * Responsible for playing the sound of a given file, in this case
	 * this is to handle the background.
	 * 
	 * @param soundName
	 */
	private void playBackground(String soundName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		//loads the sound file and plays it
		try {
			File f = new File("./" + soundName);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	        clip2 = AudioSystem.getClip();
	        clip2.open(audioInputStream);
            FloatControl volume= (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-10.0f);
	        clip2.loop(Clip.LOOP_CONTINUOUSLY);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Stop method to force sound effects clips to stop
	 * 
	 * */
	public void stop() {
		clip1.stop();
	}
	
	/**
	 * Stop method to force background sound clip to stop
	 * 
	 * */
	public void stopBackground() {
		clip2.stop();
	}
	
	/**
	 * Checks if the clip sound file has a clip running.
	 * 
	 * */
	public boolean isRunning() {
		if(clip1 != null) {
		return clip1.isRunning();
		} else {return false;}
	}
	
}
