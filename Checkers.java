package prototype;

public class Checkers {

	public static void main(String[] args) {
		Game g = new Game();
		Checkers_GUI c = new Checkers_GUI(700, 700, g);
		c.pack();
		c.setVisible(true);
	}

}