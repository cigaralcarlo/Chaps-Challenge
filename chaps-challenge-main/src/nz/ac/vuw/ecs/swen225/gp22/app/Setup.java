package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * Setup screen for Chap's Challenge. User can create a game, load a saved game, check instructions,
 * or quit.
 * 
 * @author Carlo Cigaral - 300572686
 *
 */
public class Setup extends JFrame implements MouseListener{
	
	/**
	 * Identifier used to serialize and deserialize Game class
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private Runnable clearPanel = ()->{
		panel.removeAll();
		remove(panel);
	};
	
	/**
	 * Constructor for Setup - Initializes swing configs and calls method to display MainMenu
	 */
	public Setup() {
		assert SwingUtilities.isEventDispatchThread();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		mainMenu();
		setVisible(true);
	}
	
	/**
	 * Displays main menu by drawing the Main Menu image in graphics folder
	 */
	public void mainMenu() {
		//Sets window settings
		setResizable(false);
		setSize(new Dimension(650, 450));
		setMinimumSize(new Dimension(650, 450));
		panel = new JPanel();
		addMouseListener(this);
		
		//Loads in Main Menu screen image
		try {
			BufferedImage image = ImageIO.read(new File("res/graphics/MenuScreen.png"));
			JLabel menuScreen = new JLabel(new ImageIcon(image));
			panel.add(menuScreen);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		add(panel);
		pack();
		setLocationRelativeTo(null);

	}
	
	/**
	 * Listens for mouse click events, runs code for corresponding "button image" clicked 
	 * 
	 * @param e mouse event detected
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mouseX = e.getX();
		int mouseY = e.getY();

		//User clicks on the Start Button
		if(mouseX >= 305 && mouseX <= 375 && mouseY >= 276 && mouseY <= 305) {
			this.dispose();
			new Game(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/Levels/level1.xml"));
		}
		
		//User clicks on Load button
		else if (mouseX >= 305 && mouseX <= 370 && mouseY >= 322 && mouseY <= 346) {
			this.dispose();
			
			//Runs a file chooser for user to select a saved game
			JFileChooser chooser = new JFileChooser(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/SavedGame"));
			int j = chooser.showOpenDialog(null);
			if(j == JFileChooser.APPROVE_OPTION) {
				try {
					File f = chooser.getSelectedFile();
					this.dispose();
					//Creates new game based on selected file
					new Game(Recorder.LoadSave(f));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//User clicks on the Info button
		else if (mouseX >= 310 && mouseX <= 365 && mouseY >= 364 && mouseY <= 387) {
			clearPanel.run();
			this.dispose();
			new Instructions();
		}

		//User clicks on Quit and option pop up box will appear
		else if (mouseX >= 310 && mouseX <= 365 && mouseY >= 410 && mouseY <= 432) {
			System.out.println("Quit");
			clearPanel.run();
			String[] confirm = {"Yes", "No"};
			panel = new JPanel();
			JLabel label = new JLabel("Would you like to quit?");
			panel.add(label);
			int choice = JOptionPane.showOptionDialog(null, panel, "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, confirm, confirm[1]);
			if(choice == 0) {
				System.exit(0);
			} else {
				clearPanel.run();
				mainMenu();
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
