package prototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Checkers_GUI extends JFrame {

	public static boolean DEBUG = false;
	private Dimension SIZE;
	private Game game;

	/*
	 * Panel declarations
	 */
	private JPanel mainPanel;
	private CheckersPanel checkersPanel;
	private JPanel statsPanel;
	private JPanel controlPanel;

	/*
	 * JComponents for the stats panel.
	 */
	private JLabel messageLabel, timeDisplay;
	private JTextField player1Name, player2Name;
	private JButton changeName;

	/*
	 * JComponents for the control panel.
	 */
	private JButton newGameButton, concedeButton, newGameVsAIButton, scoreboardButton;

	public Checkers_GUI(int xDimension, int yDimension, Game game, boolean debug) {
		super("Checkers");
		
		Checkers_GUI.DEBUG = debug;
		
		SIZE = new Dimension(xDimension, yDimension);
		this.setPreferredSize(SIZE);
		this.game = game;

		// initializes all of the panels and sets their size.
		panelInit();
		statsPanelInit();
		controlPanelInit();
		/*
		 * add a component listener to detect resizing and adjust/update the checkers
		 * panel accordingly.
		 */
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				SIZE = checkersPanel.getBounds().getSize();
				checkersPanel.setSize(SIZE);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});

		// add the JPanel that contains all the others.
		this.add(mainPanel);
		// Allows the x button to close the window.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// private JButton newGameButton, concedeButton, newGameVsAIButton,
	// scoreboardButton;
	private void controlPanelInit() {
		newGameButton = new JButton("New Game vs Player");
		newGameVsAIButton = new JButton("New Game vs AI");
		scoreboardButton = new JButton("Display Scoreboard");
		concedeButton = new JButton("Concede Game");

		controlPanel.add(newGameButton);
		controlPanel.add(newGameVsAIButton);
		controlPanel.add(scoreboardButton);
		controlPanel.add(concedeButton);
		
		newGameButton.setVisible(false);
		newGameVsAIButton.setVisible(false);

	}

	/*
	 * Initializes everything in the stats panel and handles the formatting
	 */
	private void statsPanelInit() {
		messageLabel = new JLabel("Welcome to Checkers!");
		timeDisplay = new JLabel("00:00");
		player1Name = new JTextField("Player 1", 12);
		player2Name = new JTextField("Player 2", 12);
		changeName = new JButton("Change Name");

		JPanel messagePane = new JPanel();
		messagePane.add(messageLabel);

		JPanel timePane = new JPanel();
		timePane.add(new JLabel("Time: "));
		timePane.add(timeDisplay);

		JPanel namePane = new JPanel();
		namePane.add(player1Name);
		namePane.add(player2Name);
		namePane.add(changeName);

		statsPanel.add(messagePane, BorderLayout.NORTH);
		statsPanel.add(timePane, BorderLayout.CENTER);
		statsPanel.add(namePane, BorderLayout.SOUTH);

	}

	private void panelInit() {
		/*
		 * This section initializes all of the JPanels and sets up their sizes and
		 * layouts.
		 */
		mainPanel = new JPanel(new BorderLayout());

		// Takes up 3/4 of the screen.
		checkersPanel = new CheckersPanel((int) (SIZE.width * .75), (int) (SIZE.height * .85), this.game);

		statsPanel = new JPanel(new BorderLayout());
		controlPanel = new JPanel(new GridLayout(0, 1));

		// the lower quarter of the screen
		statsPanel.setPreferredSize(new Dimension(SIZE.width, (int) (SIZE.height * .15)));
		// same height as board. quarter of the screen on the right.
		controlPanel.setPreferredSize(new Dimension((int) (SIZE.width * .25), (int) (SIZE.height * .75)));

		

		mainPanel.add(checkersPanel, BorderLayout.CENTER);
		mainPanel.add(statsPanel, BorderLayout.SOUTH);
		mainPanel.add(controlPanel, BorderLayout.EAST);

	}

}
