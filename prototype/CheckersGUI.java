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

/**************************************************************
 * This class holds the JFrame initilization, creates and fills the JPanels--
 * including the Checkers Panel. Handles much of the formatting and resizing.
 *
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version stable build 17 April 2018
 **************************************************************/
@SuppressWarnings("serial")
public class CheckersGUI extends JFrame implements Observer {

    /**boolean flag to toggle debug display and console output.*/
    public static boolean DEBUG = false;
    
    /** Size of the frame. not a final variable because it is resizable.*/
    private Dimension frameSize;

    /**Panel declarations.*/
    private JPanel mainPanel;
    
    /**Panel declarations.*/
    private CheckersPanel checkersPanel;
    
    /**Panel declarations.*/
    private JPanel statsPanel;
    
    /**Panel declarations.*/
    private JPanel controlPanel;

    /** JComponents for the stats panel.*/
    
    /**Simple labels to display win messages and the timer.*/
    private JLabel messageLabel, timeDisplay;
    
    /**Player name input fields.*/
    private JTextField player1Name, player2Name;
    
    /**Button to submit name change.*/
    private JButton changeName;

    /** JComponents for the control panel.*/

    /** Buttons to: Start a new game, Forfeit the game,
     * and start a new game vs AI.*/
    private JButton newGameButton, concedeButton, newGameVsAIButton;
    
    /** Buttons to toggle Debug mode and return to the last menu.*/
    private JButton debugModeButton, backButton;
    
    /** Buttons to select various game modes.*/
    private JButton freeGameMode, gameTimedMode, turnTimedMode;
    
    /**toggle the AI on or off depending on the game mode.*/
    private boolean aiMode;

    /**************************************************************
     * Constructor for the JFrame.
     *
     * @param xDimension
     *            the size of the JFrame in the X dimension.
     * @param yDimension
     *            the size of the JFrame in the Y dimension.
     * @param debug
     *            accepts the debug flag and passes it on.
     **************************************************************/
    public CheckersGUI(final int xDimension,
            final int yDimension, final boolean debug) {
        super("Checkers");

        CheckersGUI.DEBUG = debug;

        frameSize = new Dimension(xDimension, yDimension);
        this.setPreferredSize(frameSize);

        // initializes all of the panels and sets their size.
        panelInit();
        statsPanelInit();
        controlPanelInit();
        /*
         * add a component listener to detect resizing and adjust/update the
         * checkers panel accordingly.
         */
        this.addComponentListener(new GuiComponentListener());

        // add the JPanel that contains all the others.
        this.add(mainPanel);

        // Allows the x button to close the window.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**************************************************************
     * Helper method to instantiate all of the control panel (Right Panel)
     * buttons and the panel itself.
     **************************************************************/
    private void controlPanelInit() {
        newGameButton = new JButton("New Game vs Player");
        newGameVsAIButton = new JButton("New Game vs AI");
        concedeButton = new JButton("Concede Game");
        this.freeGameMode = new JButton("Free Mode");
        this.gameTimedMode = new JButton("Timed Game");
        this.turnTimedMode = new JButton("Timed Turn");
        this.backButton = new JButton("Back");
        this.debugModeButton = new JButton();
        if (CheckersGUI.DEBUG) {
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

    /**************************************************************
     * Initializes everything in the stats panel (Bottom) and handles the
     * formatting and components within.
     **************************************************************/
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

    /**************************************************************
     * Method to change the time display label and handle seconds 
     * to minutes conversion.
     *
     * @param time
     *            Time to update the label to
     **************************************************************/
    public void updateTimeDisplay(final int time) {
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

    /**************************************************************
     * Helper method to initialize panels and size management.
     **************************************************************/
    private void panelInit() {
        /*
         * This section initializes all of the JPanels and sets up their sizes
         * and layouts.
         */
        mainPanel = new JPanel(new BorderLayout());

        // Takes up 3/4 of the screen.
        checkersPanel = new CheckersPanel((int) (frameSize.width * .75),
                (int) (frameSize.height * .85), this);

        statsPanel = new JPanel(new BorderLayout());
        controlPanel = new JPanel(new GridLayout(0, 1));

        // the lower quarter of the screen
        statsPanel.setPreferredSize(
                new Dimension(frameSize.width, (int) (frameSize.height * .15)));
        // same height as board. quarter of the screen on the right.
        controlPanel.setPreferredSize(new Dimension(
                (int) (frameSize.width * .25), (int) (frameSize.height * .75)));

        mainPanel.add(checkersPanel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
        mainPanel.add(controlPanel, BorderLayout.EAST);

    }

    /**************************************************************
     * Swaps the Debug boolean flag on and off.
     **************************************************************/
    private void changeDebugMode() {
        CheckersGUI.DEBUG = !CheckersGUI.DEBUG;
        if (CheckersGUI.DEBUG) {
            this.debugModeButton.setText("Debug: on");
        } else {
            this.debugModeButton.setText("Debug: off");
        }
        this.checkersPanel.repaint();
    }

    /**************************************************************
     * This methods implements the observer pattern and updates when the
     * win/lose state occurs.
     **************************************************************/
    @Override
    public void update(final Observable o, final Object arg) {
        concedeButton.setVisible(false);
        newGameButton.setVisible(true);
        this.newGameVsAIButton.setVisible(true);
        messageLabel
                .setText("The winner is: " + this.checkersPanel.getWinner());
    }

    /**************************************************************
     * This method takes care of the process of starting a new game.
     *
     * @param mode
     *            Takes the game mode to set up.
     **************************************************************/
    private void startNewGame(final GameMode mode) {
        if (this.aiMode) {
            if (CheckersGUI.DEBUG) {
                System.out.println("Starting an AI game");
            }
            checkersPanel.newGameAI(player1Name.getText(), mode);
        } else {
            if (CheckersGUI.DEBUG) {
                System.out.println("Starting a normal game");
            }
            checkersPanel.newGame(player1Name.getText(), player2Name.getText(),
                    mode);
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

    /**************************************************************
     * This private class handles the action listener functions for all the
     * buttons in the GUI.
     **************************************************************/
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
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
                if (CheckersGUI.DEBUG) {
                    System.out.println("Against AI: " + aiMode);
                }

            } else if (src == freeGameMode) {
                if (CheckersGUI.DEBUG) {
                    System.out.println("Against AI: " + aiMode);
                }
                startNewGame(GameMode.FREE_MODE);
            } else if (src == gameTimedMode) {
                if (CheckersGUI.DEBUG) {
                    System.out.println("Against AI: " + aiMode);
                }
                startNewGame(GameMode.GAME_TIMED_MODE);
            } else if (src == turnTimedMode) {
                if (CheckersGUI.DEBUG) {
                    System.out.println("Against AI: " + aiMode);
                }
                startNewGame(GameMode.TURN_TIMED_MODE);
            } else if (src == concedeButton) {
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
                if (CheckersGUI.DEBUG) {
                    System.out.println("Against AI: " + aiMode);
                }
            } else if (src == changeName) {
                checkersPanel.setPlayersNames(player1Name.getText(),
                        player2Name.getText());
            } else if (src == backButton) {
                newGameButton.setVisible(true);
                newGameVsAIButton.setVisible(true);
                freeGameMode.setVisible(false);
                gameTimedMode.setVisible(false);
                turnTimedMode.setVisible(false);
                backButton.setVisible(false);
                concedeButton.setVisible(false);
            } else if (src == debugModeButton) {
                changeDebugMode();
            }
        }
    }

    /**************************************************************
     * This listener handles the resizing of the frame.
     **************************************************************/
    private class GuiComponentListener implements ComponentListener {

        @Override
        public void componentResized(final ComponentEvent e) {
            frameSize = checkersPanel.getBounds().getSize();
            checkersPanel.setSize(frameSize);
        }

        @Override
        public void componentMoved(final ComponentEvent e) {
        }

        @Override
        public void componentShown(final ComponentEvent e) {
        }

        @Override
        public void componentHidden(final ComponentEvent e) {
        }

    }

}
