import javax.swing.*;
import java.awt.*;
public class GameHub{

  JPanel mainPanel;
  JPanel statsPanel;
  JMenuBar menuBar;


  public static void main(String[] args){
    //Make the Main Window for the Game.
    GameFrame frame = new GameFrame("Game Hub", new Dimension(600,600));
    //set it visible.
    frame.setVisible(true);



    //Menu bar initialization.
    menuBar = new JMenuBar();
		JMenuItem exit = new JMenuItem("Exit");
		menuBar.add(exit);
		JMenuItem reset = new JMenuItem("Reset");
		menuBar.add(reset);
		JMenuItem options = new JMenuItem("Open Options Menu");
		menuBar.add(options);


    //Listener for all Menu functions.
    ActionListener menuListener = new ActionListener(){
      @Override
			public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch(cmd){
          case "exit":
          break;
          case "reset":
          break;
          case "options":
          break;
        }

			}
    };
    //Add all the Action Listeners and set Action Commands.
		exit.addActionListener(menuListener);
		exit.setActionCommand("exit");
		reset.addActionListener(menuListener);
		reset.setActionCommand("reset");
		options.addActionListener(menuListener);
		options.setActionCommand("options");
		


  }

}
