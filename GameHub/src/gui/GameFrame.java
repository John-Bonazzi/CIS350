import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{

  Dimension size;
  String title;

  public GameFrame(String title,Dimension size) {
		super(title);
    this.title = title;
    this.size = size;
    //size = new Dimension(600, 600);
		setSize(size.width, size.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

}
