package prototype;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class ScoreBoardFrame extends JFrame{
	
	/** Swing component to create tabbed windows in a single frame **/
	private JTabbedPane tp;
	
	private JPanel freeModePanel;
	
	private JPanel turnModePanel;
	private JPanel normalModePanel;
	
	private ScoreBoardData data;
	
	public ScoreBoardFrame(ScoreBoardData data) {
		this.setPreferredSize(new Dimension(400, 600));
		this.data = data;
		updateAllScores();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		freeModePanel = new JPanel();
		turnModePanel = new JPanel();
		normalModePanel = new JPanel();
		setupPanel(this.freeModePanel, GameMode.FREE_MODE);
		setupPanel(this.normalModePanel, GameMode.GAME_TIMED_MODE);
		setupPanel(this.turnModePanel, GameMode.TURN_TIMED_MODE);
		createTabs();
		this.add(this.tp);
		
		this.pack();
		this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    this.setAlwaysOnTop(true);
	}
	
	private void createTabs() {
		tp = new JTabbedPane();
		tp.addTab("Free", this.freeModePanel);
		tp.addTab("Timed", this.normalModePanel);
		tp.addTab("Turn", this.turnModePanel);
	}
	
	private void setupPanel(JPanel panel, GameMode gameMode) {
		String[] names = data.getNames(gameMode);
		int[] times = data.getTimes(gameMode);
		if(Checkers_GUI.debug) {
			System.out.println("There are: " + names.length + " names and " + times.length + " times.");
		}
		String temp = "";
		for(int i = 0; i < names.length; i++) {
			temp = names[i] + "\t:" + times[i];
			if(Checkers_GUI.debug) {
				System.out.println("Label " + i + ": " + temp);
			}
			panel.add(new JLabel(temp));
		}
	}
	
	private void updateAllScores() {
		data.updateScores(GameMode.FREE_MODE);
		data.updateScores(GameMode.GAME_TIMED_MODE);
		data.updateScores(GameMode.TURN_TIMED_MODE);
	}
}
