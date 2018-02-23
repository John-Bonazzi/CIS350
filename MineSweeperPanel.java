import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.ImageIcon;



public class MineSweeperPanel extends JPanel  {

	private JButton[][] board;
	private Cell iCell;
	private JButton quitButton;
	private MineSweeperGame game;
	private JPanel boardPanel;
	private JPanel controlPanel;
	private JButton resetButton;
	ImageIcon bomb;
	
	public MineSweeperPanel() {
		
		
		
		board = new JButton[10][10];
		quitButton = new JButton("quit");
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		//add(quitButton, c);
		
		bomb = createImageIcon("images/bombicon.png");
		
		
		boardPanel = new JPanel();
		GridLayout grid = new GridLayout(10, 10);
		boardPanel.setLayout(grid);
		for (int row = 0; row < 10; row ++) {
			for (int col = 0; col < 10; col ++) {
			board[row][col] = new JButton();
			board[row][col].addActionListener(new ButtonListener());
			boardPanel.add(board[row][col]);		
				
			}
			
			}
		
		game = new MineSweeperGame();
		
		add(boardPanel, c);
		
		displayBoard();
		
		/*********************************************/
		
		controlPanel = new JPanel();
		resetButton = new JButton("Reset");
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		controlPanel.add(resetButton, c);
		//BoxLayout box = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		//controlPanel.setLayout(grid2);
		
		
		//controlPanel.add(resetButton, grid2);
		resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		resetButton.setSize(new Dimension(50, 100));
		resetButton.addActionListener(new ButtonListener());
		c.gridx = 1;
		add(controlPanel, c);
		
		
	}
	
	private void displayBoard() {
		for (int row = 0; row < 10; row++){
			for (int col = 0; col < 10; col++){
				
				Cell iCell = game.getCell(row, col);
				if (iCell.isMine()) {
					board[row][col].setIcon(bomb);
					
					//causes problems but helps with reset
					//board[row][col].setEnabled(true);
				}
				
				else board[row][col].setIcon(null);
				
				if (iCell.isExposed() == true){
					board[row][col].setEnabled(false);
				}
				
				
				//this causes problems
				if (iCell.getMineCount() > 0){
					board[row][col].setText(Integer.toString(iCell.getMineCount()));
					board[row][col].setEnabled(false);
				}
				
				//tentative
				//if (!iCell.isMine() && !iCell.isExposed()) {
				//	board[row][col].setText(null);
				//	board[row][col].setEnabled(true);
				//}
			}
		}
	}
	
	protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MineSweeperPanel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	private class ButtonListener implements ActionListener {
		
		
		
	    public void actionPerformed(ActionEvent e){
	    	
	    	if (resetButton == e.getSource()) {
	    		reset();
	    		
	    	}
		
	    	for (int row = 0; row < 10; row++){
	    		 for (int col = 0; col < 10; col++){
	    		 if (board[row][col] == e.getSource()){
	    		 game.select(row, col);
	    		 
	    		 }
	    		 }
	    	}
	    	displayBoard();
	    }
	}
	
	public void reset() {
		
		game = new MineSweeperGame();
		
		
	
	}
	
	
}
