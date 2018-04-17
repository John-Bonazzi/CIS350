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
	private ArrayList<String> freeNames;
	private ArrayList<Integer> freeTimes;
	private ArrayList<String> normalNames;
	private ArrayList<Integer> normalTimes;
	private ArrayList<String> turnNames;
	private ArrayList<Integer> turnTimes;

	public ScoreBoardData() {
		freeNames = new ArrayList<String>();
		freeTimes = new ArrayList<Integer>();
		normalNames = new ArrayList<String>();
		normalTimes = new ArrayList<Integer>();
		turnNames = new ArrayList<String>();
		turnTimes = new ArrayList<Integer>();
		try {
			createPath(GameMode.FREE_MODE).toFile().createNewFile();
			createPath(GameMode.GAME_TIMED_MODE).toFile().createNewFile();
			createPath(GameMode.TURN_TIMED_MODE).toFile().createNewFile();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

	}

	public String[] getNames(GameMode gameMode) {
		String[] result;
		if (gameMode == GameMode.FREE_MODE) {
			result = convertToStringArray(this.freeNames);
		}
		if (gameMode == GameMode.GAME_TIMED_MODE)
			result = convertToStringArray(this.normalNames);
		else
			result = convertToStringArray(this.turnNames);
		if (Checkers_GUI.debug) {
			this.debugPrintArray(result);
		}
		return result;
	}

	public int[] getTimes(GameMode gameMode) {
		int[] result;
		if (gameMode == GameMode.FREE_MODE)
			result = convertToIntArray(this.freeTimes);
		if (gameMode == GameMode.GAME_TIMED_MODE)
			result = convertToIntArray(this.normalTimes);
		else
			result = convertToIntArray(this.turnTimes);
		return result;
	}

	public void updateScores(GameMode gameMode) {
		loadFromFile(gameMode);
	}

	public void setScores(String name, int time, GameMode gameMode) {
		if (Checkers_GUI.debug) {
			System.out.println("TESTING FOR SCOREBOARD\nName: " + name + " Time: " + time);
		}
		boolean changed;
		if (gameMode == GameMode.FREE_MODE) {
			changed = makeScores(name, time, this.freeNames, this.freeTimes);
		} else if (gameMode == GameMode.GAME_TIMED_MODE) {
			changed = makeScores(name, time, this.normalNames, this.normalTimes);
		} else {
			changed = makeScores(name, time, this.turnNames, this.turnTimes);
		}

		if (changed) {
			saveToFile(gameMode);
		}
	}

	private boolean makeScores(String name, int time, ArrayList<String> names, ArrayList<Integer> times) {
		if (times.size() == 0 && names.size() == 0) {
			times.add(new Integer(time));
			names.add(name);
		}
		if (Checkers_GUI.debug) {
			System.out.println("Looking into the scoreboard of size: " + times.size());
		}
		for (int i = 0; i < times.size(); i++) {
			if (time < times.get(i).intValue()) {
				if (Checkers_GUI.debug) {
					System.out.println("Name: " + name + " Time: " + time + " Position: " + i);
				}
				times.add(i, new Integer(time));
				names.add(i, name);
				while (times.size() > TOP_TEN) {
					times.remove(TOP_TEN);
				}
				while (names.size() > TOP_TEN) {
					names.remove(TOP_TEN);
				}
				return true;
			}
		}
		return false;
	}

	private Path createPath(GameMode gameMode) {
		Path p;
		if (gameMode == GameMode.FREE_MODE) {
			p = Paths.get("GameData", "FreeMode");
		} else if (gameMode == GameMode.GAME_TIMED_MODE) {
			p = Paths.get("GameData", "GameTimedMode");
		} else {
			p = Paths.get("GameData", "TurnTimedMode");
		}
		return p;
	}

	// SOURCE: code taken from:
	// https://docs.oracle.com/javase/tutorial/essential/io/file.html#common
	private void saveToFile(GameMode gameMode) {
		Charset charset = Charset.forName("US-ASCII");
		ArrayList<String> names;
		ArrayList<Integer> times;
		if (gameMode == GameMode.FREE_MODE) {
			names = this.freeNames;
			times = this.freeTimes;
		} else if (gameMode == GameMode.GAME_TIMED_MODE) {
			names = this.normalNames;
			times = this.normalTimes;
		} else {
			names = this.turnNames;
			times = this.turnTimes;
		}
		String s;
		try {
			if (!createPath(gameMode).toFile().exists()) {
				createPath(gameMode).toFile().createNewFile();
			}
			BufferedWriter writer = Files.newBufferedWriter(createPath(gameMode), charset);
			for (int i = 0; i < names.size(); i++) {
				s = makeDataString(names, times, i);
				writer.write(s, 0, s.length());
				writer.newLine();
			}
			writer.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	// SOURCE: code partially taken from:
	// https://docs.oracle.com/javase/tutorial/essential/io/file.html#common
	private void loadFromFile(GameMode gameMode) {
		Charset charset = Charset.forName("US-ASCII");
		String[] tempBuffer;
		ArrayList<String> tempNames = new ArrayList<String>();
		ArrayList<Integer> tempTimes = new ArrayList<Integer>();
		try (BufferedReader reader = Files.newBufferedReader(createPath(gameMode), charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (Checkers_GUI.debug) {
					System.out.println(line);
				}
				line = line.trim();
				tempBuffer = line.split(" ", 2);
				if (Checkers_GUI.debug) {
					System.out.println("Name on File: " + tempBuffer[0] + "\nTime on File: " + tempBuffer[1]);
				}
				tempNames.add(tempBuffer[0]);
				tempTimes.add(new Integer(Integer.parseInt(tempBuffer[1])));
			}
			reader.close();
			if (gameMode == GameMode.FREE_MODE) {
				this.freeNames = tempNames;
				this.freeTimes = tempTimes;
			} else if (gameMode == GameMode.GAME_TIMED_MODE) {
				this.normalNames = tempNames;
				this.normalTimes = tempTimes;
			} else {
				this.turnNames = tempNames;
				this.turnTimes = tempTimes;
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	private String makeDataString(ArrayList<String> names, ArrayList<Integer> times, int index) {
		String result = "";
		result += names.get(index);
		result += " ";
		result += times.get(index).intValue();
		if (Checkers_GUI.debug) {
			System.out.println(result);
		}
		return result;
	}

	private int[] convertToIntArray(ArrayList<Integer> list) {
		int[] result = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			if (Checkers_GUI.debug) {
				System.out.println("Converted value: " + list.get(i));
			}
			result[i] = list.get(i).intValue();
		}
		return result;
	}

	private String[] convertToStringArray(ArrayList<String> list) {
		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
			if (Checkers_GUI.debug) {
				System.out.println("Converted value: " + result[i]);
			}
		}
		if(Checkers_GUI.debug) {
			debugPrintArray(result);
		}
		return result;
	}

	private void debugPrintArray(String[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.println("String at index " + i + ": " + arr[i]);
		}
	}
}
