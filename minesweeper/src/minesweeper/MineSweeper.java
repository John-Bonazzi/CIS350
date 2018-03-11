package minesweeper;

import java.io.IOException;

import javax.swing.JFrame;

public class MineSweeper {

    /**
     * Setting up the size and number of bombs for the game and then starting the Minesweeper Game
     * @param argv 
     * @throws IOException
     */
    public static void main(String[] argv) throws IOException {
        Model model;
            model = new Model(6, 6, 3);

        //Creating the View
        View view = new View(model);

        JFrame frame = new JFrame("Minesweeper");
        frame.setContentPane(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

    }
}
