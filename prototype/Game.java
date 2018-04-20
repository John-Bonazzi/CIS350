package prototype;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/***************************************************************
 * This holds the logic behind the game, for example the button Logic, if the
 * game is running, and the current player.
 *
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version stable build 17 April 2018
 ***************************************************************/
public class Game extends Observable implements Observer {
    
	/** Time limit for timed game mode. **/
    private final int freeTimedMode = 3600;
    
    /** The time on each players turn. **/
    private final int gameTimedMode = 300;
    
    /** A list of players. **/
    private final int turnTimedMode = 20;
    
    /** A list of players. **/
    private ArrayList<Player> players;
    
    /** Indicates whether the game is running or not. **/
    private boolean gameRunning = false;
    
    /** The current player's color. **/
    private ColorStatus currentPlayer;
    
    /** The current time. **/
    private int time;
    
    /** Game mode in which there is no time limit. **/
    private GameMode gameMode = GameMode.FREE_MODE;

    /***************************************************************
     * Constructor for a Game object. adds the players, panels, and sets current
     * player to white. it also sets the time.
     *
     * @param panel
     *            One of the Graphical user interface panels
     * @param gui
     *            Creates a instance of the GUI.
     * @param againstAI
     *            Determines if the user is playing against the AI or not
     ***************************************************************/
    public Game(final CheckersPanel panel, final CheckersGUI gui,
            final boolean againstAI) {
        players = new ArrayList<Player>();
        this.addObserver(panel);
        this.addObserver(gui);
        this.gameRunning = true;
        players.add(new Player("White", ColorStatus.WHITE));
        if (againstAI) {
            players.add(new CheckersAI(ColorStatus.BLACK));
        } else {
            players.add(new Player("Black", ColorStatus.BLACK));
        }
        this.currentPlayer = ColorStatus.WHITE;
        setTime();
    }

    /***************************************************************
     * Constructor for a Game object.
     * It differs from the default constructor because
     * it does not use any swing element, used
     * for testing purposes.
     * @param againstAI Determines if the user is playing against the AI or not
     ***************************************************************/
    public Game(final boolean againstAI) {
		players = new ArrayList<Player>();
		this.gameRunning = true;
		players.add(new Player("White", ColorStatus.WHITE));
		if (againstAI) {
			players.add(new CheckersAI(ColorStatus.BLACK));
		} else {
			players.add(new Player("Black", ColorStatus.BLACK));
		}
		this.currentPlayer = ColorStatus.WHITE;
		setTime();
	}
    
    /***************************************************************
     * Get method for the list of players.
     *
     * @return The list of players.
     ***************************************************************/
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /***************************************************************
     * Sets the players names within the GUI.
     *
     * @param player1 the first Player's name.
     * @param player2 the second Player's name.
     ***************************************************************/
    public void setNames(final String player1, final String player2) {
        this.players.get(0).setName(player1);
        this.players.get(1).setName(player2);
    }

    /***************************************************************
     * getter for gamemode.
     *
     * @return the current game mode.
     ***************************************************************/
    public GameMode getMode() {
        return this.gameMode;
    }

    /***************************************************************
     * Get method for the gameRunning field. The value indicates whether the
     * game is currently running or not. Note that the game is limited to the
     * board, not the GUI, it is possible to modify the GUI elements when the
     * game is not running, but not the board.
     *
     * @return true if the game is running, false otherwise
     ***************************************************************/
    public boolean isGameRunning() {
        return gameRunning;
    }

    /***************************************************************
     * The method is called when starting a new game. It will create a new Game
     * object, new Players and set the names for the Players. Note that the
     * previous game does not need to end, or to be stopped, in order to start a
     * new game.
     *
     * @param name1
     *            Player 1's name
     * @param name2
     *            Player 2's name
     ***************************************************************/
    public void startGame(final String name1, final String name2) {
        setTime();
        this.gameRunning = true;
        this.players.clear();
        players.add(new Player(name1, ColorStatus.WHITE));
        players.add(new Player(name2, ColorStatus.BLACK));
        this.currentPlayer = ColorStatus.WHITE;
        setChanged();
        notifyObservers();
    }

    /***************************************************************
     * this method sets the game mode in which the user wants to play.
     *
     * @param option A valid game mode.
     ****************************************************************/
    public void setGameMode(final GameMode option) {
        this.gameMode = option;
    }

    /***************************************************************
     * Starts the AI game mode.
     *
     * @param name the user's name.
     ****************************************************************/
    public void startGameAI(final String name) {
        setTime();
        this.gameRunning = true;
        this.players.clear();
        players.add(new Player(name, ColorStatus.WHITE));
        players.add(new CheckersAI(ColorStatus.BLACK));
        this.currentPlayer = ColorStatus.WHITE;
        setChanged();
        notifyObservers();
    }

    /***************************************************************
     * Stop the current game.
     ***************************************************************/
    public void stopGame() {
        this.gameRunning = false;
        setChanged();
        notifyObservers();
    }

    /***************************************************************
     * Go through the list of players and return the player whose color matches
     * the currentPlayer's color. Note, it could return null in the case the
     * list is empty, or none of the players in the list have a color matching
     * currentPlayer. The null value is used for testing purposes, since it is
     * assumed that during a game the list can't be empty, and that there is one
     * white and one black player.
     *
     * @return the current Player object, or null if there is no player matching
     *         currentPlayer.
     ****************************************************************/
    public Player getCurrentPlayer() {
        for (Player p : players) {
            if (p.playerColor() == currentPlayer) {
                return p;
            }
        }
        return null;
    }

    /***************************************************************
     * This method sets the next player to its turn.
     ****************************************************************/
    public void nextPlayer() {
        if (this.currentPlayer == ColorStatus.WHITE) {
            this.currentPlayer = ColorStatus.BLACK;
        } else if (this.currentPlayer == ColorStatus.BLACK) {
            this.currentPlayer = ColorStatus.WHITE;
        }
        if (this.gameMode == GameMode.TURN_TIMED_MODE) {

            // the plus one is to show the 20 seconds on the timer.
            this.time = this.turnTimedMode + 1;
        }
    }

    /***************************************************************
     * A getter for the time.
     *
     * @return the current time for the game
     ****************************************************************/
    public int getTime() {
        return this.time;
    }

    /***************************************************************
     * Updates the time of the game.
     ****************************************************************/
    public void updateTime() {
        if (this.gameMode == GameMode.FREE_MODE) {
            this.time++;

            // The free game mode still has a time limit of 60 minutes.
            if (time >= this.freeTimedMode) {
                setChanged();
                notifyObservers();
            }
        } else {
            this.time--;

            if (time <= 0) {
                setChanged();
                notifyObservers();
            }
        }
    }

    /***************************************************************
     * this method calls the stopgame() method.
     *
     * @param o
     *            The Observable
     * @param arg
     *            The argument being passed
     ****************************************************************/
    @Override
    public void update(final Observable o, final Object arg) {
        stopGame();
    }

    /***************************************************************
     * sets the time based on the game mode being selected.
     ****************************************************************/
    private void setTime() {
        if (gameMode == GameMode.FREE_MODE) {
            this.time = 0;
        } else if (gameMode == GameMode.GAME_TIMED_MODE) {
            this.time = this.gameTimedMode;
        } else {
            this.time = this.turnTimedMode;
        }
    }

}
