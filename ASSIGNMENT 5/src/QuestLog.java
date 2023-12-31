/**
 *NAME        :       JEET AGRAWAL
 *NSID        :       jea316
 *STUDENT ID  :       11269096
 */

import com.opencsv.CSVReader;
import lib280.base.Pair280;
import lib280.hashtable.KeyedChainedHashTable280;
import lib280.tree.OrderedSimpleTree280;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

// This project uses a JAR called opencsv which is a library for reading and
// writing CSV (comma-separated value) files.
//
// You don't need to do this for this project, because it's already done, but
// if you want to use opencsv in other projects on your own, here's the process:
//
// 1. Download opencsv-3.1.jar from http://sourceforge.net/projects/opencsv/
// 2. Drag opencsv-3.1.jar into your project.
// 3. Right-click on the project in the package explorer, select "Properties" (at bottom of popup menu)
// 4. Choose the "Libraries" tab
// 5. Click "Add JARs"
// 6. Select the opencsv-3.1.jar from within your project from the list.
// 7. At the top of your .java file add the following imports:
//        import java.io.FileReader;
//        import com.opencsv.CSVReader;
//
// Reference documentation for opencsv is here:
// http://opencsv.sourceforge.net/apidocs/overview-summary.html


public class QuestLog extends KeyedChainedHashTable280<String, QuestLogEntry> {

	private Object Pair280;

	public QuestLog() {
		super();
	}

	/**
	 * Obtain an array of the keys (quest names) from the quest log.  There is
	 * no expectation of any particular ordering of the keys.
	 *
	 * @return The array of keys (quest names) from the quest log.
	 */
	public String[] keys() {
		// TODO Implement this method.
		goFirst();
		String[] temp = new String[count()];

		for (int i = 0; i < count(); i++){
			temp[i] = this.itemKey();
			goForth();
		}
		return temp;
	}

	/**
	 * Format the quest log as a string which displays the quests in the log in
	 * alphabetical order by name.
	 *
	 * @return A nicely formatted quest log.
	 */
	public String toString() {
		// TODO Implement this method.
		String[] temp = this.keys();
		Arrays.parallelSort(temp);
		String x = "";
		for (int i = 0;i < temp.length;i++){
			if (i == temp.length - 1){
				x += this.obtain(temp[i]);
			}
			else {
				x += this.obtain(temp[i]) + "\n";
			}
		}
		return x;

		// Remove this line you're ready.  It's just to prevent compiler errors.
	}

	/**
	 * Obtain the quest with name k, while simultaneously returning the number of
	 * items examined while searching for the quest.
	 * @param k Name of the quest to obtain.
	 * @return A pair in which the first item is the QuestLogEntry for the quest named k, and the
	 *         second item is the number of items examined during the search for the quest named k.
	 *         Note: if no quest named k is found, then the first item of the pair should be null.
	 */
	public Pair280<QuestLogEntry, Integer> obtainWithCount(String k) {
		// TODO Implement this method.

		this.search(k);
		int count = 1;

		this.itemListLocation.goFirst();

		while (this.itemExists() && !this.itemListLocation.item().questName.equals(k)) {
			itemListLocation.goForth();
			count++;
		}

		if (this.itemListLocation.item() == null){
			return new Pair280<>(null,1);
		}

		return new Pair280<>(this.itemListLocation.item(), count);

		// Write a method that returns a Pair280 which contains the quest log entry with name k,
		// and the number QuestLogEntry objects that were examined in the process.  You need to write
		// this method from scratch without using any of the superclass methods (mostly because
		// the superclass methods won't be terribly useful unless you can modify them, which you
		// aren't allowed to do!).


//		return null;  // Remove this line you're ready.  It's just to prevent compiler errors.
	}


	public static void main(String args[])  {
		// Make a new Quest Log
		QuestLog hashQuestLog = new QuestLog();

		// Make a new ordered binary lib280.tree.
		OrderedSimpleTree280<QuestLogEntry> treeQuestLog =
				new OrderedSimpleTree280<QuestLogEntry>();


		// Read the quest data from a CSV (comma-separated value) file.
		// To change the file read in, edit the argument to the FileReader constructor.
		CSVReader inFile;
		try {
			//input filename on the next line - path must be relative to the working directory reported above.
			inFile = new CSVReader(new FileReader("/Users/jeetagrawal/Desktop/U OF S/WINTER 2020/CMPT 280/ASSIGNMENTS/ASSIGNMENT 5/src/quests100000.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			return;
		}

		String[] nextQuest;
		try {
			// Read a row of data from the CSV file
			while ((nextQuest = inFile.readNext()) != null) {
				// If the read succeeded, nextQuest is an array of strings containing the data from
				// each field in a row of the CSV file.  The first field is the quest name,
				// the second field is the quest region, and the next two are the recommended
				// minimum and maximum level, which we convert to integers before passing them to the
				// constructor of a QuestLogEntry object.
				QuestLogEntry newEntry = new QuestLogEntry(nextQuest[0], nextQuest[1],
						Integer.parseInt(nextQuest[2]), Integer.parseInt(nextQuest[3]));
				// Insert the new quest log entry into the quest log.
				hashQuestLog.insert(newEntry);
				treeQuestLog.insert(newEntry);
			}
		} catch (IOException e) {
			System.out.println("Something bad happened while reading the quest information.");
			e.printStackTrace();
		}

		// Print out the hashed quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
//		System.out.println(hashQuestLog);

		// Print out the lib280.tree quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
//	    System.out.println(treeQuestLog.toStringInorder());


	    // (call hashQuestLog.obtainWithCount() for each quest in the log and find average # of access)
		hashQuestLog.goFirst();

		int total = 0;
		int hashItem = 0;
		int treeItem = 0;
		while (hashQuestLog.itemExists()){
			total++;
			hashItem += hashQuestLog.obtainWithCount(hashQuestLog.item().questName).secondItem();
			treeItem += treeQuestLog.searchCount(hashQuestLog.item());
			hashQuestLog.goForth();
		}

		System.out.println("Avg . # of items examined per query in the hashed quest log with "+total+" entries : "+(float)hashItem/total);
		System.out.println("Avg . # of items examined per query in the tree quest log with "+total+" entries : "+(float)treeItem/total);
	}
}