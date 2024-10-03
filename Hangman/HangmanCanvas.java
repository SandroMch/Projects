
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	//for initializing
	private GLabel hiddenWord = new GLabel("");
	private GLabel wrongAnswers = new GLabel("");
	double startX;
	double startY;
	double endY;
	private GCompound hang;
	private GOval HEAD = null;
	private GLine BODY = null;
	private GLine LEFT_HAND = null;
	private GLine LEFT_FINGERS = null;
	private GLine RIGHT_HAND = null;
	private GLine RIGHT_FINGERS = null;
	private GLine LEFT_LEG = null;
	private GLine RIGHT_LEG = null;
	private GLine LEFT_FOOT = null;
	private GLine RIGHT_FOOT = null;
	private GLine LEFT_HIP = null;
	private GLine RIGHT_HIP = null;
	
	/** Resets the display so that only the scaffold appears */
	public void reset() {								//resets game.
		removeAll();
		startAgain();

		wrongAnswers.setLabel("");
		hiddenWord.setLabel("");
		wrongAnswers.setFont("LONDON-15");
		hiddenWord.setFont("LONDON-30");
		hang = new GCompound();
		drawScaffold();
		add(hang, 0, 0);
		add(hiddenWord);
		add(wrongAnswers);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {				//adds word on canvas
		hiddenWord.setLabel(word);
		add(hiddenWord);
	}
	// this method draws scaffold beam and construction of hanging place
	private void drawScaffold() {
		startX = getWidth()/ 8 + 10;
		startY = 20;
		endY = startY + SCAFFOLD_HEIGHT;
		GLine scaffold = new GLine(startX, startY, startX, endY);
		add(scaffold);
		GLine beam = new GLine(startX, startY, startX + BEAM_LENGTH, startY);
		add(beam);
		GLine rope = new GLine(startX + BEAM_LENGTH, startY, startX + BEAM_LENGTH, startY + ROPE_LENGTH);
		add(rope);
		hiddenWord.setFont("LONDON-25");
		hiddenWord.setLocation(startX, endY + 50);
		hang.add(scaffold);
		hang.add(beam);
		hang.add(rope);
		
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	//this method is for incorrect guesses when answer is wrong it adds one body part
	public void noteIncorrectGuess(char letter) {
		double newStartX = startX + BEAM_LENGTH;
		double newStartY = startY + ROPE_LENGTH;
		wrongAnswers.setFont("LONDON-15");
		wrongAnswers.setLocation(startX, endY + 70);
		String temp = wrongAnswers.getLabel();
		if (!temp.contains(Character.toString(letter))) {
			temp += letter;
		}
		wrongAnswers.setLabel(temp);
		add(wrongAnswers);
		if (HEAD == null) {
			HEAD = new GOval(HEAD_RADIUS * 2, HEAD_RADIUS * 2);
			HEAD.setFilled(true);
			HEAD.setFillColor(Color.white);
			add(HEAD, newStartX - HEAD_RADIUS, newStartY);
		} else if (BODY == null) {
			BODY = new GLine(newStartX, newStartY + 2 * HEAD_RADIUS, newStartX,
					newStartY + BODY_LENGTH + 2 * HEAD_RADIUS);
			add(BODY);
		} else if (LEFT_HAND == null) {
			LEFT_HAND = new GLine(newStartX, newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS,
					newStartX - UPPER_ARM_LENGTH, newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS);
			LEFT_FINGERS = new GLine(newStartX - UPPER_ARM_LENGTH, newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS,
					newStartX - UPPER_ARM_LENGTH,
					newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS + LOWER_ARM_LENGTH);
			add(LEFT_HAND);
			add(LEFT_FINGERS);
		} else if (RIGHT_HAND == null) {
			RIGHT_HAND = new GLine(newStartX, newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS,
					newStartX + UPPER_ARM_LENGTH, newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS);
			RIGHT_FINGERS = new GLine(newStartX + UPPER_ARM_LENGTH, newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS,
					newStartX + UPPER_ARM_LENGTH,
					newStartY + ARM_OFFSET_FROM_HEAD + 2 * HEAD_RADIUS + LOWER_ARM_LENGTH);
			add(RIGHT_HAND);
			add(RIGHT_FINGERS);
		} else if (LEFT_LEG == null) {
			LEFT_HIP = new GLine(newStartX, newStartY + BODY_LENGTH + 2 * HEAD_RADIUS, newStartX - HIP_WIDTH,
					newStartY + BODY_LENGTH + 2 * HEAD_RADIUS);
			LEFT_LEG = new GLine(newStartX - HIP_WIDTH, newStartY + BODY_LENGTH + 2 * HEAD_RADIUS,
					newStartX - HIP_WIDTH, newStartY + BODY_LENGTH + 2 * HEAD_RADIUS + LEG_LENGTH);
			add(LEFT_HIP);
			add(LEFT_LEG);

		} else if (RIGHT_LEG == null) {
			RIGHT_HIP = new GLine(newStartX, newStartY + BODY_LENGTH + 2 * HEAD_RADIUS, newStartX + HIP_WIDTH,
					newStartY + BODY_LENGTH + 2 * HEAD_RADIUS);
			RIGHT_LEG = new GLine(newStartX + HIP_WIDTH, newStartY + BODY_LENGTH + 2 * HEAD_RADIUS,
					newStartX + HIP_WIDTH, newStartY + BODY_LENGTH + 2 * HEAD_RADIUS + LEG_LENGTH);
			add(RIGHT_HIP);
			add(RIGHT_LEG);
		} else if (LEFT_FOOT == null) {
			LEFT_FOOT = new GLine(newStartX - HIP_WIDTH - FOOT_LENGTH,
					newStartY + BODY_LENGTH + 2 * HEAD_RADIUS + LEG_LENGTH, newStartX - HIP_WIDTH,
					newStartY + BODY_LENGTH + 2 * HEAD_RADIUS + LEG_LENGTH);
			add(LEFT_FOOT);
		} else if (RIGHT_FOOT == null) {
			RIGHT_FOOT = new GLine(newStartX + HIP_WIDTH + FOOT_LENGTH,
					newStartY + BODY_LENGTH + 2 * HEAD_RADIUS + LEG_LENGTH, newStartX + HIP_WIDTH,
					newStartY + BODY_LENGTH + 2 * HEAD_RADIUS + LEG_LENGTH);
			add(RIGHT_FOOT);
		}

	}

	private void startAgain() {       //this makes body part null again to work
		HEAD = null;
		BODY = null;
		LEFT_HAND = null;
		LEFT_FINGERS = null;
		RIGHT_HAND = null;
		RIGHT_FINGERS = null;
		LEFT_LEG = null;
		RIGHT_LEG = null;
		LEFT_FOOT = null;
		RIGHT_FOOT = null;
		LEFT_HIP = null;
		RIGHT_HIP = null;
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
