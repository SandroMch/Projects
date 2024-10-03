
/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.StringTokenizer;


public class NameSurferEntry implements NameSurferConstants {
	String name = "";
	int[] allDecades = new int[NDECADES];

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers
	 * giving the rank of that name for each decade.
	 */
	public NameSurferEntry(String line) {						// I devide text first part is name Second part is their ranks
		StringTokenizer tk = new StringTokenizer(line);			// I used tokenizer for that.
		name = tk.nextToken();
		int index = 0;
		while (tk.hasMoreTokens()) {
			int currNum = Integer.parseInt(tk.nextToken());		// I put ranks in Array to get them later
			allDecades[index] = currNum;
			index++;
		}
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The
	 * decade value is an integer indicating how many decades have passed since
	 * the first year in the database, which is given by the constant
	 * START_DECADE. If a name does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return allDecades[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		String nameAndNum = name + " [ ";
		for (int i = 0; i < NDECADES; i++) {
			nameAndNum += allDecades[i] + " ";
		}
		return nameAndNum + "]";
	}
}
