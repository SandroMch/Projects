
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Hangman extends ConsoleProgram {
	// this part is for initializing
	HangmanLexicon newWord;
	private int guessesLeft = 8;;
	private String wordToGuess;
	String guessedLetters = "";
	private String hiddenWord;
	char letter;
	private int guesses = 8;
	String wrongAnswers = "";
	private HangmanCanvas canvas;
	RandomGenerator rgen = RandomGenerator.getInstance();
	public void init() {
		canvas = new HangmanCanvas();		
		canvas.reset();
		add(canvas);
	}
	
	public void run() {
		findWord();    			//this method finds word
		sayHello();				//this method prints hello and makes game work
	}

	private void sayHello() {
		println("Welcome to Hangman!");
		hiddenWord = "";
		for (int i = 0; i < wordToGuess.length(); i++) {		//this method hides word
			hiddenWord += "-";
		}
		while (guesses > 0 && !hiddenWord.equals(wordToGuess)) {		// this method is for playing 
			setup(wordToGuess);											// untill we have lives 
		}
	}

	private void findWord() {
		newWord = new HangmanLexicon();
		wordToGuess = newWord.getWord(rgen.nextInt(0,newWord.getWordCount() - 1)); // finds random word
	}
	

	private void setup(String wordToGuess) {
		println("The word now looks like this: " + hiddenWord);				//prints how word looks while playing
		println("You have " + guesses + " guesses left.");					//how many guesses left
		String oldWord = hiddenWord;
		char yourGuess = readLine("Your guess: ").toUpperCase().charAt(0);	//it's for put letter that we guessed
		while (yourGuess < 'A' || yourGuess > 'Z') {
			println("Please put a single charachter");
			yourGuess = readLine("Enter character again: ").toUpperCase().charAt(0);
		}
		for (int i = 0; i < wordToGuess.length(); i++) {							//this shows what word looks like after guess
			char currChar = wordToGuess.charAt(i);
			if (currChar == yourGuess) {
				hiddenWord = afterGuess(yourGuess, hiddenWord, i);
			}
		}
		if (guessedLetters.contains(Character.toString(yourGuess))) {			//this is for wrong answers
			println("You already guessed this character: " + yourGuess);
		} else if (oldWord.equals(hiddenWord)) {
			println("There are no " + yourGuess + "'s in the word");			//if letter is wrong - 1 life
			guesses--;
			canvas.noteIncorrectGuess(yourGuess);								//and this draws body part on canvas
			if (guesses == 0) {
				println("You are completely hung.");							//for game over
				println("The word was " + wordToGuess);
				println("You lose");
				String continuePlaying = readLine("Do you want to continue playing ? : ").toUpperCase();
				if(continuePlaying.equals("YES")) {
					startAgain();
				}
				
			}
		} else {
			guessedLetters += yourGuess;										//if guess is correct it changes hidden words look
			println("Your guess is correct.");
			if (hiddenWord.equals(wordToGuess)) {
				println("You guessed the word: " + wordToGuess);
				println("You win.");
				
				String continuePlaying = readLine("Do you want to continue playing ?: ");
				if(continuePlaying.equals("YES")) {
					startAgain();
				}
			}
		}
		canvas.displayWord(hiddenWord);											//this puts hidden word on canvas.
	}

	private String afterGuess(char yourGuess, String hiddenWord, int i) {
		hiddenWord = hiddenWord.substring(0, i) + yourGuess + hiddenWord.substring(i + 1);	//this method returns word after guess
		return hiddenWord;
	}
	private void startAgain() {														//this method restarts game
		guesses = guessesLeft;
		canvas.reset();
		wordToGuess = newWord.getWord(rgen.nextInt(0,newWord.getWordCount() - 1));
		guessedLetters = "";
		sayHello();
	}

}
