package spellingAid;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps tracks of all the history of the session. Including all the
 * words that was spelled
 * 
 * Singleton Class as there can only be one instance of it.
 * 
 * Responsibilities: 
 * 1. Store all the words and their successStatus. (and their level). 
 * 2. Allows a JTable model to fetch information from it
 * 
 * @author Daniel
 *
 */
public class Statistics {
	private static Statistics _statistics = null;
	private StatsTableModel _statsTableModel;
	private List<List<WordStats>> _masterList = new ArrayList<>();
	private int _level = 0;
	private static final String[] columnNames = { "Word", "Success", "Faults", "Fails" };

	/**
	 * Private constructor as it is a singleton Initialise all the lists in the
	 * masterlist to prevent errors
	 */
	private Statistics() {
		for (int i = 0; i < 11; i++) {
			_masterList.add(new ArrayList<>());
		}
	}

	/**
	 * Singleton so passing an instance of itself to other people
	 * 
	 * @return
	 */
	public static Statistics getInstance() {
		if (_statistics == null) {
			_statistics = new Statistics();
		}
		return _statistics;
	}

	/**
	 * Takes a TableModel and stores it in a field for firing off changes
	 * 
	 * @param statsTableModel
	 */
	public void passModel(StatsTableModel statsTableModel) {
		_statsTableModel = statsTableModel;
	}

	/**
	 * Takes a list of Word objects and their corresponding level. Add the stats
	 * of the word into the master list.
	 * 
	 * @param newWords
	 * @param level
	 */
	public void recordQuizResults(List<Word> newWords, int level) {
		List<WordStats> currentLevel = _masterList.get(level - 1);

		// Loop through the wordlist and see if duplicates exist
		for (Word currentWord : newWords) {
			for (WordStats currentWordStat : currentLevel) {
				if (currentWordStat.getWord().equals(currentWord)) {
					// Get the result of the word and change the stat
					// the ordinal should correspond to the array index of the
					// result
					currentWordStat.changeStat(currentWord.getSuccessStatus().ordinal());
					break;
				}
			}

			// Create a new WordStats object, increment the corresponding stat
			// then add it
			// to the master list
			WordStats newWordStats = new WordStats(currentWord);
			newWordStats.changeStat(currentWord.getSuccessStatus().ordinal());
			currentLevel.add(newWordStats);
		}

		_statsTableModel.fireTableDataChanged();
	}

	/**
	 * Set the level to view in the Statistics table
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		_level = level-1;
		_statsTableModel.fireTableDataChanged();
	}

	/**
	 * Method for the JTable method in StatsTableModel
	 * 
	 * @param index
	 * @return
	 */
	public String getColumnHeadings(int index) {
		return columnNames[index];
	}

	/**
	 * Method for the JTable method in StatsTableModel
	 * 
	 * @return count
	 */
	public int getRowCount() {
		int count = 0;

		for (List<WordStats> currentList : _masterList) {
			for (WordStats currentWord : currentList) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Method for the JTable method in StatsTableModel. Reads the current level
	 * set by setLevel() method and returns the values in that level
	 * 
	 * @param rows
	 * @param columns
	 * @return
	 */
	public Object getValueAt(int rows, int columns) {
		int count = 0;
		List<WordStats> currentList = _masterList.get(_level);
		
		for (WordStats currentWord : currentList) {
			if (count == rows) {
				if (columns == 0) {
					return currentWord.getWord().toString();
				} else if (columns == 1) {
					return currentWord.getSuccess();
				} else if (columns == 2) {
					return currentWord.getFault();
				} else {
					return currentWord.getFail();
				}
			}
			count++;
		}

		return null;
	}
}
