package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class to display instructions window - shows key binds
 * 
 * @author Carlo Cigaral - 300572686
 *
 */
public class Instructions extends JFrame implements ActionListener{

	/**
	 * Identifier used to serialize and deserialize Game class
	 */
	private static final long serialVersionUID = 1L;
	JButton back;
	JPanel panel;
	
	Runnable clearPanel = ()->{
		panel.removeAll();
		remove(panel);
	};
	
	/**
	 * Constructor for instructions - Initializes swing configurations and calls method to display Instructions
	 */
	public Instructions() {
		assert SwingUtilities.isEventDispatchThread();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		instructions();
		setVisible(true);
	}

	/**
	 * Listens for action event, brings user back to main menu if button click is detected
	 * 
	 * @param e Action Event detected
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			clearPanel.run();
			this.dispose();
			new Setup();
		}		
	}

	/**
	 * Displays image to show controls
	 */
	public void instructions() {
		//Sets window settings
		setResizable(false);
		setSize(new Dimension(650, 450));
		setMinimumSize(new Dimension(650, 450));
		panel = new JPanel();
		
		//Loads in Info Screen image
		try {
			BufferedImage image = ImageIO.read(new File("res/graphics/InfoScreen.png"));
			JLabel infoScreen = new JLabel(new ImageIcon(image));
			panel.add(infoScreen);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		back = new JButton("Back");
		back.addActionListener(this);
		
		add(BorderLayout.SOUTH, back);
		add(panel);
		pack();
		setLocationRelativeTo(null);

	}
	
	
	
}
