import java.util.Observable;



public abstract class Board extends Observable{
	final private int BOARD_SIZE = 8;
	private int[BOARD_SIZE][BOARD_SIZE] board;
	private int field_id;
    private int posx;
    private int posy;
    private Game game;
    
    public Board(Game game, int x, int y, int field_id) {
        this.field_id = field_id; //The value of each cell. 1 is black, 2 is white, 0 is nothing.
        this.posx = x;
        this.posy = y;
        this.game = game;
    }

    public void Init() {
    		for(int c = 0; c < BOARD_SIZE; c++) {
    			for(int r = 0;)
    		}
    }


}
