package prototype;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ScoreBoardData {
	private final int TOP_TEN = 10;
	private ArrayList<String> names;
	private ArrayList<Integer> times;

	public ScoreBoardData() {
		names = new ArrayList<String>();
		times = new ArrayList<Integer>();
	}

	public String[] getNames() {
		return convertToStringArray(this.names);
	}

	public int[] getTimes() {
		return convertToIntArray(this.times);
	}

	public void updateScores(GameMode gameMode) {
		loadFromFile(createPath(gameMode));
	}

	public void setScores(String name, int time, GameMode gameMode) {
		boolean changed = false;
		for (int i = 0; i < this.times.size(); i++) {
			if (time < this.times.get(i).intValue()) {
				this.times.add(i, new Integer(time));
				this.names.add(i, name);
				while(this.times.size() > TOP_TEN) {
					this.times.remove(TOP_TEN);
				}
				while(this.names.size() > TOP_TEN) {
					this.names.remove(TOP_TEN);
				}
				changed = true;
				break;
			}
		}
		if (changed) {
			// the absolute value and the % 3 prevents the program from misbehaving and
			// crash if a wrong value is passed.
			saveToFile(createPath(gameMode));
		}
	}

	private Path createPath(GameMode gameMode) {
		Path p;
		if (gameMode == GameMode.FREE_MODE) {
			p = Paths.get("../GameData/FreeMode");
		} else if (gameMode == GameMode.GAME_TIMED_MODE) {
			p = Paths.get("../GameData/GameTimedMode");
		} else {
			p = Paths.get("../GameData/TurnTimedMode");
		}
		return p;
	}

	//SOURCE: code taken from: https://docs.oracle.com/javase/tutorial/essential/io/file.html#common
	private void saveToFile(Path path) {
		Charset charset = Charset.forName("US-ASCII");
		String s = makeDataString();
		try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
		    writer.write(s, 0, s.length());
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}

	//SOURCE: code partially taken from: https://docs.oracle.com/javase/tutorial/essential/io/file.html#common
	private void loadFromFile(Path path) {
		Charset charset = Charset.forName("US-ASCII");
		String[] tempBuffer;
		ArrayList<String> tempNames = new ArrayList<String>();
		ArrayList<Integer> tempTimes = new ArrayList<Integer>();
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        line = line.trim();
		        tempBuffer = line.split(" ", 2);
		        tempNames.add(tempBuffer[0]);
		        tempTimes.add(new Integer(Integer.parseInt(tempBuffer[1])));
		    }
		    this.names = tempNames;
		    this.times = tempTimes;
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}

	private String makeDataString() {
		String result = "";
		int minutes;
		int seconds;
		for(int i = 0; i < this.names.size(); i++) {
			result += this.names.get(i);
			result += "\t\t";
			minutes = this.times.get(i).intValue() / 60;
			if(minutes < 10) {
				result += ("0" + minutes);
			}
			else {
				result += minutes;
			}
			result += ":";
			seconds =  this.times.get(i).intValue() % 60;
			if(seconds < 10) {
				result += ("0" + seconds);
			}
			else {
				result += seconds;
			}
			result += "/n";
		}
		return result;
	}
	
	private int[] convertToIntArray(ArrayList<Integer> list) {
		int[] result = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i).intValue();
		}
		return result;
	}

	private String[] convertToStringArray(ArrayList<String> list) {
		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}
}
