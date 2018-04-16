package prototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

//This panel should be the same logic as the existing checkers game.
public class CheckersPanel extends JPanel {

	private Dimension size;

	public void setSize(Dimension size) {
		this.size = size;
		initBoard();
		repaint();
	}

	private final float PERCENTAGE = (float) 0.75;
	private Board board;
	private Game game;
	private ColorStatus[][] checkerColor;
	private int boardX, boardY;
	private int boardWidth, boardHeight;
	private int tileSize;
	private boolean[][] canMove;
	private boolean[][] options;

	private Graphics g;

	public CheckersPanel(int xSize, int ySize, Game g) {
		size = new Dimension(xSize, ySize);
		this.setPreferredSize(size);

		// this.setBackground(Color.RED);
		this.game = g;
		this.board = new Board(this.game);
		this.initBoard();
		this.addMouseListener(new MListener());
		canMove = board.canSelect(game.getCurrentPlayer());
		options = canMove;
	}

	private void initBoard() {

		int centerX = (int) (this.size.getWidth() / 2);
		int centerY = (int) (this.size.getHeight() / 2);
		boardX = (int) (centerX - (centerX * PERCENTAGE));
		boardY = (int) (centerY - (centerY * PERCENTAGE));

		double widOrHei = Math.min(size.width, size.height);

		boardWidth = (int) (widOrHei * PERCENTAGE);
		boardHeight = (int) (widOrHei * PERCENTAGE);

		tileSize = boardWidth / Board.SIZE;
	}

	public void paintComponent(Graphics graph) {
		g = graph;

		g.setColor(Color.RED);

		// Draw the initial Board
		this.paintBoard(boardX, boardY, boardWidth, boardHeight);

		// Draw pieces
		this.paintPieces(boardX, boardY, boardWidth, boardHeight);

		// Highlight available pieces
		this.highlightCheckers(this.options);

	}

	private void highlightCheckers(boolean[][] brd) {

		// The highlighting color.
		Color cy = Color.CYAN;

		// Draw a filled square for each true value in the matrix.
		for (int row = 0; row < Board.SIZE; row++) {
			for (int col = 0; col < Board.SIZE; col++) {
				if (brd[row][col]) {
					highlightSquare(col, row, cy);
				}
			}
		}

	}

	private void highlightSquare(int x, int y, Color c) {
		this.g.setColor(new Color(c.getRed(), c.getBlue(), c.getGreen(), 100));
		this.g.fillRect(boardX + x * tileSize, boardY + y * tileSize, tileSize, tileSize);
	}

	private void paintBoard(int xI, int yI, int wid, int hei) {
		/*
		 * board Colors f0f1ff 1 square color aab1e6 2 square color 000000 white piece
		 * ffffff black piece
		 */
		// Color lightBlue = new Color(240,240,255);
		// Color darkBlue = new Color(170,177,230);
		Color lightBrown = new Color(210, 180, 140);
		Color darkBrown = new Color(153, 102, 0);

		for (int row = 0; row < Board.SIZE; row++) {
			for (int col = 0; col < Board.SIZE; col++) {

				if (row % 2 == col % 2)
					g.setColor(darkBrown);
				else
					g.setColor(lightBrown);
				g.fillRect(xI + 1 + row * tileSize, yI + 1 + col * tileSize, tileSize, tileSize);

			}
		}
	}

	private void paintPieces(int xI, int yI, int wid, int hei) {

		checkerColor = board.getBoard();
		int x, y, w, h;
		int sx, sy;
		w = (int) (tileSize * PERCENTAGE);
		h = (int) (tileSize * PERCENTAGE);

		int fontSize = 30;

		for (int row = 0; row < Board.SIZE; row++) {
			for (int col = 0; col < Board.SIZE; col++) {
				y = row * tileSize + yI + (tileSize - w) / 2;
				x = col * tileSize + xI + (tileSize - h) / 2;
				sy = row * tileSize + xI + (tileSize / 2) - (fontSize / 3);
				sx = col * tileSize + yI + (tileSize / 2) + (fontSize / 3);
				switch (checkerColor[row][col]) {
				case WHITE_KING:
					g.setColor(Color.WHITE);
					g.fillOval(x, y, w, h);
					g.setColor(Color.BLACK);
					g.setFont(new Font("SansSerif", Font.BOLD, fontSize));
					g.drawString("K", sx, sy);
					break;
				case WHITE:
					g.setColor(Color.WHITE);
					g.fillOval(x, y, w, h);
					break;
				case BLACK_KING:
					g.setColor(Color.BLACK);
					g.fillOval(x, y, w, h);
					g.setColor(Color.WHITE);
					g.setFont(new Font("SansSerif", Font.BOLD, fontSize));
					g.drawString("K", sx, sy);
					break;
				case BLACK:
					g.setColor(Color.BLACK);
					g.fillOval(x, y, w, h);
					break;
				case EMPTY:
					if (Checkers_GUI.DEBUG) {
						g.setColor(Color.RED);
						g.fillRect(x, y, w, h);
					}
					break;
				}
			}
		}
	}

	/*
	 * prints out misc. debug information.
	 */
	public void paintDebug() {
		g.setColor(Color.RED);
		g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
		g.fillRect(0, 0, size.width, size.height);

		g.drawLine(boardX, 0, boardX, size.height);
		g.drawLine(boardWidth + boardX, 0, boardWidth + boardX, size.height);

		g.drawLine(0, boardY, size.width, boardY);
		g.drawLine(0, boardHeight + boardY, size.width, boardHeight + boardY);

		int posx, posy;
		posx = 1;
		posy = 2;
		highlightSquare(posx, posy, Color.RED);
		System.out.println(board.canMove(ColorStatus.BLACK, ColorStatus.BLACK_KING, posx, posy));
	}

	private class MListener implements MouseListener {

		private boolean first = true;
		boolean didJump = false;
		private int originalRow;
		private int originalCol;

		@Override
		public void mouseReleased(MouseEvent e) {
			int mx, my;
			mx = e.getX();
			my = e.getY();
			if (mx >= boardX && mx <= boardWidth + boardX) {
				if (my >= boardY && my <= boardWidth + boardY) {

					int relX, relY;

					relX = mx - boardX - 2; // loss of precision with integer conversion
					relY = my - boardY - 2; // loss of precision with integer conversion

					int col, row;
					col = relX / tileSize;
					row = relY / tileSize;
					// options = canMove; // set to null because it checks for it in paintComponent.
					// board.showOptions(game.getCurrentPlayer(), -1, -1); //sets all to false
					if (Checkers_GUI.DEBUG) {
						System.out.println("SELECTED VALUE: " + checkerColor[row][col]);
						System.out.println("COORDINATES: " + row + " ROW " + col + " COLUMN.");
					}
					
					//The position selected is a true (valid) position and it's the first action in the turn.
					if (options[row][col] && first) {
						this.originalRow = row;
						this.originalCol = col;
						options = board.showOptions(game.getCurrentPlayer(), row, col);
						this.first = false;
					} 
					//The position selected is a true (valid) position and a checker has been selected.
					else if (!first && options[row][col]) {
						
						//If the selected move is the original checker.
						if (col == this.originalCol && row == this.originalRow) {
							this.first = true;
							options = canMove;
						} 
						else {

							// Prevent a player from doing more than 2 jumps in one turn.
							if (didJump) {
								board.move(game.getCurrentPlayer(), this.originalRow, this.originalCol, row, col);
								didJump = false;
							} else {
								didJump = board.move(game.getCurrentPlayer(), this.originalRow, this.originalCol, row,
										col);
							}

							// If there was a jump, and the same checker can jump again, show the moves for
							// that checker only
							if (didJump && board.canJump(game.getCurrentPlayer().playerColor(),
									game.getCurrentPlayer().kingColor(), row, col)) {
								canMove = board.canSelect(game.getCurrentPlayer());
								options = board.showOptions(game.getCurrentPlayer(), row, col);
								this.originalCol = col;
								this.originalRow = row;
							} else {
								game.nextPlayer();
								canMove = board.canSelect(game.getCurrentPlayer());
								options = canMove;
								first = true;
							}
						}
					}
					repaint();
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

}
