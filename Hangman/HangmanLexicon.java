
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {
	private ArrayList<String> word = new ArrayList<String>();

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		addWords();
		return word.size();
	}

	private void addWords() {						//this method reads words from lexicon and adds to the arrayList
		try {
			BufferedReader bf = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				try {
					String text = bf.readLine();
					if (text == null) {
						break;
					}
					word.add(text);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {			//returns word 
		return word.get(index);
	}
}
