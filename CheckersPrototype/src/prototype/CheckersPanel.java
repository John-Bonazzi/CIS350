package prototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/*
 * 
 * Write a method that highlights a square based on a boolean 2d array.
 * 
 * add buttons and text fields.
 * 
 * 
 */







//This panel should be the same logic as the existing checkers game.
public class CheckersPanel extends JPanel implements MouseListener {

	private Dimension size;

	private final float PERCENTAGE = (float) 0.75;
	private Board board;
	private ColorStatus[][] checkerColor;
	private int boardX, boardY;
	private int boardWidth, boardHeight;
	private int tileSize;

	public CheckersPanel(int xSize, int ySize) {
		size = new Dimension(xSize, ySize);
		this.setPreferredSize(size);

		// this.setBackground(Color.RED);
		board = new Board();
		this.initBoard();
		this.addMouseListener(this);

	}

	private void initBoard() {

		int centerX = (int) (this.size.getWidth() / 2);
		int centerY = (int) (this.size.getHeight() / 2);
		boardX = (int) (centerX - (centerX * PERCENTAGE));
		boardY = (int) (centerY - (centerY * PERCENTAGE));

		boardWidth = (int) (size.width * PERCENTAGE);
		boardHeight = (int) (size.height * PERCENTAGE);

		tileSize = boardWidth / Board.SIZE;
	}

	public void paintComponent(Graphics g) {
		// g.setColor(Color.RED);
		// g.fillRect(0,0,(int)size.getWidth(),(int)size.getHeight());
		g.setColor(Color.RED);

		// g.fillRect(0, 0, size.width, size.height);

		g.drawLine(boardX, 0, boardX, size.height);
		g.drawLine(boardWidth + boardX, 0, boardWidth + boardX, size.height);

		g.drawLine(0, boardY, size.width, boardY);
		g.drawLine(0, boardHeight + boardY, size.width, boardHeight + boardY);

		// g.fillOval(centerX, centerY, 10, 10);
		g.setColor(Color.BLACK);
		g.drawRect(boardX, boardY, boardWidth + 1, boardWidth + 1);

		// Draw the initail Board
		this.paintBoard(g, boardX, boardY, boardWidth, boardHeight); // ts is the tile size.
		// Draw pieces
		this.paintPieces(g, boardX, boardY, boardWidth, boardHeight);

		// hightlight available pieces

		// highlight legal moves

	}

	private void paintBoard(Graphics g, int xI, int yI, int wid, int hei) {
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

	private void paintPieces(Graphics g, int xI, int yI, int wid, int hei) {

		checkerColor = board.getBoard();
		int x, y, w, h;
		int sx, sy;
		w = (int) (tileSize * PERCENTAGE);
		h = (int) (tileSize * PERCENTAGE);

		int fontSize = 30;

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				x = j * tileSize + xI + (tileSize - w) / 2;
				y = i * tileSize + yI + (tileSize - h) / 2;
				sx = j * tileSize + xI + tileSize / 2 - (fontSize / 3);
				sy = i * tileSize + yI + tileSize / 2 + (fontSize / 3);
				switch (checkerColor[i][j]) {
				case WHITE_KING:
					g.setColor(Color.BLACK);
					g.setFont(new Font("SansSerif", Font.BOLD, fontSize));
					g.drawString("K", sx, sy);
				case WHITE:
					g.setColor(Color.WHITE);
					g.fillOval(x, y, w, h);
					break;
				case BLACK_KING:
					g.setColor(Color.WHITE);
					g.setFont(new Font("SansSerif", Font.BOLD, fontSize));
					g.drawString("K", sx, sy);
				case BLACK:
					g.setColor(Color.BLACK);
					g.fillOval(x, y, w, h);
					break;

				}

			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

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

				// System.out.println(mx+", "+my);

				int tileX, tileY;
				tileX = relX / tileSize;
				tileY = relY / tileSize;

			}
		}
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
