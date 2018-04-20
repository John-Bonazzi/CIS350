package prototype;

/***************************************************************
 * Checkers class, this is where the main method is and how the entire program
 * starts.
 *
 * @author Gionata Bonazzi
 * @author Cole Sellers
 * @author Brendan Cronan
 * @author Rosa Fleming
 * @version stable build 17 April 2018
 ***************************************************************/
public class Checkers {

    /***************************************************************
     * Main method which starts the Graphical User interface.
     * @param args command line arguments.
     ***************************************************************/
    public static void main(final String[] args) {
        CheckersGUI c = new CheckersGUI(700, 700, false);
        c.setVisible(true);
    }

}
