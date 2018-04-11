package prototype;
import java.awt.*;
import javax.swing.*;
public class Checkers_GUI extends JFrame{

  public final Dimension SIZE;

  private JPanel mainPanel;
  private CheckersPanel checkersPanel;
  private JPanel statsPanel;
  private JPanel controlPanel;

  public Checkers_GUI(int xDimension, int yDimension){
    super("Checkers");
    
    SIZE=new Dimension(xDimension,yDimension);
    this.setPreferredSize(SIZE);
    this.setResizable(false);
    mainPanel=new JPanel(new BorderLayout());

    //Takes up 3/4 of the screen.
    checkersPanel= new CheckersPanel((int)(SIZE.width*.75),(int)(SIZE.height*.75));
    statsPanel = new JPanel(new GridLayout());
    controlPanel= new JPanel(new GridLayout());

    //the lower quarter of the screen
    statsPanel.setPreferredSize(new Dimension(SIZE.width,(int) (SIZE.height*.25)));
    //same height as board.  quarter of the screen on the right.
    controlPanel.setPreferredSize(new Dimension((int)(SIZE.width*.25),(int)(SIZE.height*.75)));
    
    statsPanel.setBackground(Color.GREEN);
    controlPanel.setBackground(Color.BLUE);



    mainPanel.add(checkersPanel,BorderLayout.CENTER);
    mainPanel.add(statsPanel, BorderLayout.SOUTH);
    mainPanel.add(controlPanel, BorderLayout.EAST);
    this.add(mainPanel);
    this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    this.pack();
  }

  public static void main(String[] args) {
	  Checkers_GUI c=new Checkers_GUI(700,700);
	  c.setVisible(true);

  }




}
