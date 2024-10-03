
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;
import acm.util.MediaTools;
import acm.util.RandomGenerator;

public class Extension extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 600;
	public static final int APPLICATION_HEIGHT = 700;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static int NTURNS = 3;
	/** paddle */
	private GRoundRect paddle;
	/** for initializing */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private static final int POINT = 5;
	private double vy;
	private GOval ball;
	private double vx;
	private GRect brick;
	private int NUMBRICKS = NBRICKS_PER_ROW * NBRICK_ROWS;
	private int currScore = 0;
	private int currLives = 3;
	GObject upLeft;
	GObject upRight;
	GObject downLeft;
	GObject downRight;
	GObject brickSide;
	GLabel scoreBoard;
	GLabel lifeBoard;
	/**For audio*/
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	AudioClip gameOverSound = MediaTools.loadAudioClip("gameover.au.au");
	AudioClip gameWinSound = MediaTools.loadAudioClip("winingSound.au");


	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
	/*this methods makes whole program run */
		setBackground(Color.black);				//Sets background black
		addLives();								//adds live bar
		addScoreBoard();						//adds scoreBoard
		setup();								//This is for bricks and also paints bricks
		addPaddle();							//This adds paddle
		addMouseListeners();					
		addBall();								//This adds ball
		startPlaying();							//And this method makes ball move bounce etc.
	}
	/*We need double for loop to draw 10X10 brick wall and I paint it with coordinates because
	 * I know starting and ending coordinates        */
	private void setup() {
		double startX = (getWidth() -( NBRICKS_PER_ROW  * BRICK_WIDTH + (NBRICKS_PER_ROW - 1) * BRICK_SEP))/ 2;
		double endX = getWidth() - BRICK_WIDTH;
		double endY = BRICK_Y_OFFSET + NBRICK_ROWS * BRICK_HEIGHT + BRICK_SEP * (NBRICK_ROWS - 1);
		for (double i = startX; i <= endX; i += BRICK_SEP + BRICK_WIDTH) {
			for (double j = BRICK_Y_OFFSET; j <= endY; j += BRICK_SEP + BRICK_HEIGHT) {
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				add(brick, i, j);
				if (j <= BRICK_Y_OFFSET + 2 * BRICK_HEIGHT + BRICK_SEP) {
					brick.setColor(Color.red);
				}
				if (j > BRICK_Y_OFFSET + 2 * BRICK_HEIGHT + BRICK_SEP
						&& j <= BRICK_Y_OFFSET + 4 * BRICK_HEIGHT + 3 * BRICK_SEP) {
					brick.setColor(Color.orange);
				}
				if (j > BRICK_Y_OFFSET + 4 * BRICK_HEIGHT + 3 * BRICK_SEP
						&& j <= BRICK_Y_OFFSET + 6 * BRICK_HEIGHT + 5 * BRICK_SEP) {
					brick.setColor(Color.YELLOW);
				}
				if (j > BRICK_Y_OFFSET + 6 * BRICK_HEIGHT + 5 * BRICK_SEP
						&& j <= BRICK_Y_OFFSET + 8 * BRICK_HEIGHT + 7 * BRICK_SEP) {
					brick.setColor(Color.green);
				}
				if (j > BRICK_Y_OFFSET + 8 * BRICK_HEIGHT + 7 * BRICK_SEP
						&& j <= BRICK_Y_OFFSET + 10 * BRICK_HEIGHT + 9 * BRICK_SEP) {
					brick.setColor(Color.CYAN);
				}
			}
		}
	}
	/*With this method I create paddle   */
	private void addPaddle() {
		paddle = new GRoundRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.white);
		add(paddle, getWidth() / 2 - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	}
	/*This moves paddle with mouse and paddle has Y coordinate always same  */
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= PADDLE_WIDTH / 2 && e.getX() <= getWidth() - PADDLE_WIDTH / 2) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	/*This method adds ball */
	private void addBall() {
		ball = new GOval(2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.white);
		add(ball, getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
	}
	/*This method makes ball move also makes ball bounce from walls, roof and paddle and prints 
	 * game win and game lose */
	private void startPlaying() {
		ballSpeed();																	//This method is for ball speed
		while (NTURNS != 0) {
			if (getCollidingObject() == paddle && getCollidingObject() != scoreBoard
					&& getCollidingObject() != lifeBoard) {										//if ball touches paddle's corners 	
				vy = -1 * Math.abs(vy);
				bounceClip.play();														//ball will stuck in the paddle with this method ball wont stuck and will be bounced from paddle
			}
			if (getCollidingObject() != null && getCollidingObject() != paddle &&
					getCollidingObject() != scoreBoard && 
					getCollidingObject() != lifeBoard) {								//This method checks colliding objects and remove
				NUMBRICKS--;															//if colliding object isnt paddle or null
				remove(getCollidingObject());
				bounceClip.play();														//And I also count remained bricks
				currScore += POINT;
				scoreBoard.setLabel("Score: " + Integer.toString((int)currScore));		//this refreshes score board when score is added
			}
			minusLives();																//This method checks lives and if ball touches down wall minus 1 life
			ballMove();																	//This method moves ball			
			checkWalls();																//This method checks walls and changes ball's vx and vy
			if (gameWin()) {
				break;
			}
			if (gameOver()) {
				break;
			}
		}
	}
	/*This 2 methods makes ball move with the speed of vx and vy */
	private void ballMove() {
		ball.move(vx, vy);
		ball.pause(7);
	}

	private void ballSpeed() {
		vx = rgen.nextDouble(1.7, 3.5);
		vy = 3;
	}
	/*This method controls ball's directions if  ball touches left or right wall vx is changed
	 * if ball touches upper wall or paddle vy is changed and also if ball touches bricks left or right side
	 * vx is changed this way balls bounce looks logical */
	private void checkWalls() {
		if (ball.getX() <= 0 || ball.getX() >= getWidth() - 2 * BALL_RADIUS - vx
				|| (getCollidingObject() == upLeft && getCollidingObject() == downLeft && getCollidingObject() != null)
				|| (getCollidingObject() == upRight && getCollidingObject() == downRight
						&& getCollidingObject() != null) && getCollidingObject() != scoreBoard
						&& getCollidingObject() != lifeBoard) {
			vx *= -1;
			bounceClip.play();								//Adds sound effect
		} else if (getCollidingObject() != null && getCollidingObject() != paddle && 
				getCollidingObject() != scoreBoard && getCollidingObject() != lifeBoard) {
			vy *= -1;
		}
		if (ball.getY() <= 0 && getCollidingObject() != scoreBoard
				&& getCollidingObject() != lifeBoard) {
			vy *= -1;
			bounceClip.play();															//Adds sound effect
		}
	}
	/*If ball touches down wall - 1 life */
	private void minusLives() {
		if (ball.getY() >= getHeight() - 2 * BALL_RADIUS - vy) {
			ball.setLocation(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
			NTURNS--;
			currLives -= 1;
			lifeBoard.setLabel("LIFE: " + Integer.toString((int)currLives));	//this refreshes life board 
		}																		//when life is decreased
	}
	/* this method checks rectangle's corners around the ball and returns which corner
	 * touched something that isn't null
	 */
	private GObject getCollidingObject() {
		upLeft = getElementAt(ball.getX(), ball.getY());
		downRight = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		upRight = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		downLeft = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		if (upLeft != null) {
			return upLeft;
		}
		if (downLeft != null) {
			return downLeft;
		}
		if (upRight != null) {
			return upRight;
		}

		if (downRight != null) {
			return downRight;
		}
		return null;
	}
	/*This method prints game over after 0 life is left and removes ball */
	private boolean gameOver() {
		if (NTURNS == 0) {
			GLabel gameOver = new GLabel("GAME OVER ! ! !");
			gameOver.setFont("LONDON-40");
			gameOver.setColor(Color.white);
			add(gameOver, getWidth() / 2 - gameOver.getWidth() / 2, 
					getHeight() / 2 - gameOver.getAscent() / 2);
			remove(ball);
			remove(paddle);
			gameOverSound.play();									//Adds sound effect
			return true;
		}
		return false;
	}
	/*This method prints game win when every brick is broken and removes ball */
	private boolean gameWin() {
		if (NUMBRICKS == 0) {
			GLabel youWin = new GLabel("YOU WIN ! ! ! ");
			youWin.setFont("LONDON-40");
			youWin.setColor(Color.white);
			add(youWin, getWidth() / 2 - youWin.getWidth() / 2,
					getHeight() / 2 - youWin.getAscent() / 2);
			remove(ball);
			remove(paddle);
			gameWinSound.play();								//Adds sound effect
			return true;
		}
		return false;
	}
	/*adds score board */
	private void addScoreBoard() {
		scoreBoard = new GLabel("SCORE: " + Integer.toString((int)currScore));
		scoreBoard.setFont("LONDON-18");
		scoreBoard.setColor(Color.yellow);
		add(scoreBoard , 10 , scoreBoard.getAscent() + 20);
	}
	/*adds life board */
	private void addLives() {
		lifeBoard = new GLabel("LIFE: " + Integer.toString((int)currLives));
		lifeBoard.setFont("LONDON-18");
		lifeBoard.setColor(Color.red);
		add(lifeBoard , getWidth() - lifeBoard.getWidth() - 10 , lifeBoard.getAscent() + 20);
	}
}
