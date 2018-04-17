package prototype;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Checkers_GUI extends JFrame implements Observer {

	public static boolean debug = false;
	private Dimension frameSize;

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
	// private JPanel timePane;
	private JTextField player1Name, player2Name;
	private JButton changeName;

	/*
	 * JComponents for the control panel.
	 */
	private JButton newGameButton, concedeButton, newGameVsAIButton;

	private JButton debugModeButton, backButton;
	private JButton freeGameMode, gameTimedMode, turnTimedMode;
	
	boolean aiMode;

	public Checkers_GUI(int xDimension, int yDimension, boolean debug) {
		super("Checkers");

		Checkers_GUI.debug = debug;

		frameSize = new Dimension(xDimension, yDimension);
		this.setPreferredSize(frameSize);

		// initializes all of the panels and sets their size.
		panelInit();
		statsPanelInit();
		controlPanelInit();
		/*
		 * add a component listener to detect resizing and adjust/update the checkers
		 * panel accordingly.
		 */
		this.addComponentListener(new GuiComponentListener());

		// add the JPanel that contains all the others.
		this.add(mainPanel);

		// Allows the x button to close the window.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	// private JButton newGameButton, concedeButton, newGameVsAIButton,
	// scoreboardButton;
	private void controlPanelInit() {
		newGameButton = new JButton("New Game vs Player");
		newGameVsAIButton = new JButton("New Game vs AI");
		concedeButton = new JButton("Concede Game");
		this.freeGameMode = new JButton("Free Mode");
		this.gameTimedMode = new JButton("Timed Game");
		this.turnTimedMode = new JButton("Timed Turn");
		this.backButton = new JButton("Back");
		this.debugModeButton = new JButton();
		if (Checkers_GUI.debug) {
			this.debugModeButton.setText("Debug: on");
		} else {
			this.debugModeButton.setText("Debug: off");
		}

		controlPanel.add(this.concedeButton);
		controlPanel.add(this.debugModeButton);
		controlPanel.add(this.newGameButton);
		controlPanel.add(this.newGameVsAIButton);
		controlPanel.add(this.freeGameMode);
		controlPanel.add(this.gameTimedMode);
		controlPanel.add(this.turnTimedMode);
		controlPanel.add(this.backButton);

		this.newGameButton.addActionListener(new ButtonListener());
		this.concedeButton.addActionListener(new ButtonListener());
		this.newGameVsAIButton.addActionListener(new ButtonListener());
		this.changeName.addActionListener(new ButtonListener());
		this.debugModeButton.addActionListener(new ButtonListener());
		this.freeGameMode.addActionListener(new ButtonListener());
		this.gameTimedMode.addActionListener(new ButtonListener());
		this.turnTimedMode.addActionListener(new ButtonListener());
		this.backButton.addActionListener(new ButtonListener());

		this.newGameButton.setVisible(false);
		this.newGameVsAIButton.setVisible(false);
		this.freeGameMode.setVisible(false);
		this.turnTimedMode.setVisible(false);
		this.gameTimedMode.setVisible(false);
		this.backButton.setVisible(false);

	}

	/*
	 * Initializes everything in the stats panel and handles the formatting
	 */
	private void statsPanelInit() {
		messageLabel = new JLabel("Welcome to Checkers!");
		timeDisplay = new JLabel("00:00");
		player1Name = new JTextField("White", 12);
		player2Name = new JTextField("Black", 12);
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

	public void updateTimeDisplay(int time) {
		int minutes, seconds;
		minutes = time / 60;
		seconds = time % 60;
		String minutesLabel = "" + minutes;
		String secondsLabel = "" + seconds;
		if (minutes < 10) {
			minutesLabel = "0" + minutes;
		}
		if (seconds < 10) {
			secondsLabel = "0" + seconds;
		}
		this.timeDisplay.setText(minutesLabel + ":" + secondsLabel);

	}

	private void panelInit() {
		/*
		 * This section initializes all of the JPanels and sets up their sizes and
		 * layouts.
		 */
		mainPanel = new JPanel(new BorderLayout());

		// Takes up 3/4 of the screen.
		checkersPanel = new CheckersPanel((int) (frameSize.width * .75), (int) (frameSize.height * .85), this);

		statsPanel = new JPanel(new BorderLayout());
		controlPanel = new JPanel(new GridLayout(0, 1));

		// the lower quarter of the screen
		statsPanel.setPreferredSize(new Dimension(frameSize.width, (int) (frameSize.height * .15)));
		// same height as board. quarter of the screen on the right.
		controlPanel.setPreferredSize(new Dimension((int) (frameSize.width * .25), (int) (frameSize.height * .75)));

		mainPanel.add(checkersPanel, BorderLayout.CENTER);
		mainPanel.add(statsPanel, BorderLayout.SOUTH);
		mainPanel.add(controlPanel, BorderLayout.EAST);

	}

	private void changeDebugMode() {
		Checkers_GUI.debug = !Checkers_GUI.debug;
		if (Checkers_GUI.debug) {
			this.debugModeButton.setText("Debug: on");
		} else {
			this.debugModeButton.setText("Debug: off");
		}
		this.checkersPanel.repaint();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		concedeButton.setVisible(false);
		newGameButton.setVisible(true);
		this.newGameVsAIButton.setVisible(true);
		messageLabel.setText("The winner is: " + this.checkersPanel.getWinner());
	}

	private void startNewGame(GameMode mode) {
		if (this.aiMode) {
			if(Checkers_GUI.debug) {
				System.out.println("Starting an AI game");
			}
			checkersPanel.newGameAI(player1Name.getText(), mode);
		} else {
			if(Checkers_GUI.debug) {
				System.out.println("Starting a normal game");
			}
			checkersPanel.newGame(player1Name.getText(), player2Name.getText(), mode);
		}
		this.newGameVsAIButton.setVisible(false);
		this.newGameButton.setVisible(false);
		this.freeGameMode.setVisible(false);
		this.gameTimedMode.setVisible(false);
		this.turnTimedMode.setVisible(false);
		this.backButton.setVisible(false);
		this.concedeButton.setVisible(true);
		messageLabel.setText("Welcome to Checkers!");
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();
			if (src == newGameButton) {
				aiMode = false;
				newGameButton.setVisible(false);
				newGameVsAIButton.setVisible(false);
				freeGameMode.setVisible(true);
				gameTimedMode.setVisible(true);
				turnTimedMode.setVisible(true);
				backButton.setVisible(true);
				concedeButton.setVisible(false);
				if(Checkers_GUI.debug) {
					System.out.println("Against AI: " + aiMode);
				}

			} else if (src == freeGameMode) {
				if(Checkers_GUI.debug) {
					System.out.println("Against AI: " + aiMode);
				}
				startNewGame(GameMode.FREE_MODE);
			}
			else if (src == gameTimedMode) {
				if(Checkers_GUI.debug) {
					System.out.println("Against AI: " + aiMode);
				}
				startNewGame(GameMode.GAME_TIMED_MODE);
			}
			else if(src == turnTimedMode) {
				if(Checkers_GUI.debug) {
					System.out.println("Against AI: " + aiMode);
				}
				startNewGame(GameMode.TURN_TIMED_MODE);
			}
			else if (src == concedeButton) {
				checkersPanel.endGame();
				newGameVsAIButton.setVisible(true);
				newGameButton.setVisible(true);
				concedeButton.setVisible(false);
			} else if (src == newGameVsAIButton) {
				aiMode = true;
				newGameButton.setVisible(false);
				newGameVsAIButton.setVisible(false);
				freeGameMode.setVisible(true);
				gameTimedMode.setVisible(true);
				turnTimedMode.setVisible(true);
				backButton.setVisible(true);
				concedeButton.setVisible(false);
				if(Checkers_GUI.debug) {
					System.out.println("Against AI: " + aiMode);
				}
			}
			else if (src == changeName) {
				checkersPanel.setPlayersNames(player1Name.getText(), player2Name.getText());
			}
			else if (src == backButton) {
				newGameButton.setVisible(true);
				newGameVsAIButton.setVisible(true);
				freeGameMode.setVisible(false);
				gameTimedMode.setVisible(false);
				turnTimedMode.setVisible(false);
				backButton.setVisible(false);
				concedeButton.setVisible(false);
			}
			else if (src == debugModeButton) {
				changeDebugMode();
			}
		}
	}

	private class GuiComponentListener implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent e) {
			frameSize = checkersPanel.getBounds().getSize();
			checkersPanel.setSize(frameSize);
		}

		@Override
		public void componentMoved(ComponentEvent e) {
		}

		@Override
		public void componentShown(ComponentEvent e) {
		}

		@Override
		public void componentHidden(ComponentEvent e) {
		}

	}

}
