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
	//private JPanel timePane;
	private JTextField player1Name, player2Name;
	private JButton changeName;

	/*
	 * JComponents for the control panel.
	 */
	private JButton newGameButton, concedeButton, newGameVsAIButton, scoreboardButton;

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

		newGameButton.addActionListener(new ButtonListener());
		concedeButton.addActionListener(new ButtonListener());

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

	public void updateTimeDisplay(int time) {
		int minutes, seconds;
		minutes = time / 60;
		seconds = time % 60;
		String minutesLabel = "" + minutes;
		String secondsLabel = "" + seconds;
		if(minutes < 10) {
			minutesLabel = "0" + minutes;
		}
		if(seconds < 10) {
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
	
	@Override
	public void update(Observable o, Object arg) {
		concedeButton.setVisible(false);
		newGameButton.setVisible(true);
		messageLabel.setText("The winner is: " + this.checkersPanel.getWinner());
	}


	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();
			if (src == newGameButton) {
				checkersPanel.newGame(player1Name.getText(), player2Name.getText());
				concedeButton.setVisible(true);
				newGameButton.setVisible(false);
				messageLabel.setText("Welcome to Checkers!");
			}
			else if (src == concedeButton) {
				checkersPanel.endGame();
				newGameButton.setVisible(true);
				concedeButton.setVisible(false);
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
		public void componentMoved(ComponentEvent e) {}

		@Override
		public void componentShown(ComponentEvent e) {}

		@Override
		public void componentHidden(ComponentEvent e) {}

	}


}
