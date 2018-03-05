package gui;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

  Dimension size;
  String title;

  public Frame(String title,Dimension size) {
		super(title);
    this.title = title;
    this.size = size;
    //size = new Dimension(600, 600);
		setSize(size.width, size.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

}
