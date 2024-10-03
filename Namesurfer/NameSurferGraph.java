
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.util.RandomGenerator;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	/*
	 * this part is for initializing
	 */
	double lineGap = (double) (getWidth() / NDECADES);
	ArrayList<NameSurferEntry> nameList;
	double startingX;
	GLabel name;
	GLabel secName;

	
	/*
	 * this method draws graphics mini lines it start coordinate is leftVertical line and end Y coordinate
	 * is RightVertical line
	 * if rank is 0 we have to write names on the down horizontal line and if not write is on graph
	 * and I use switch loop to color different graph
	 */
	private void drawGraphic(NameSurferEntry n, int nextColor) {
		int currRank = 0;
		for (double xCoordinate = 0; xCoordinate <= getWidth() - 2 * lineGap; xCoordinate += lineGap) {
			double startingY = findGraphCoordinate(n.getRank(currRank));
			double endY = findGraphCoordinate(n.getRank(currRank + 1));
			GLine graphLine = new GLine(xCoordinate, startingY, xCoordinate + lineGap, endY);

			if (n.getRank(currRank) == 0) {
				name = new GLabel(n.getName() + " " + "*");
			} else {
				name = new GLabel(n.getName() + " " + n.getRank(currRank));
			}
			if (n.getRank(currRank + 1) == 0) {
				secName = new GLabel(n.getName() + " " + "*");
			} else {
				secName = new GLabel(n.getName() + " " + n.getRank(currRank + 1));
			}
			if (currRank < 9) {
				currRank++;
			}
			name.setFont("LONDON-12");
			secName.setFont("LONDON-12");
			add(name, xCoordinate, startingY);
			add(secName, xCoordinate + lineGap, endY);
			add(graphLine);

			switch (nextColor) {
			case 1:
				name.setColor(Color.blue);
				secName.setColor(Color.blue);
				graphLine.setColor(Color.blue);
				break;
			case 2:
				name.setColor(Color.green);
				secName.setColor(Color.green);
				graphLine.setColor(Color.green);
				break;
			case 3:
				name.setColor(Color.red);
				secName.setColor(Color.red);
				graphLine.setColor(Color.red);
				break;
			case 4:
				name.setColor(Color.gray);
				secName.setColor(Color.gray);
				graphLine.setColor(Color.gray);
				break;
			case 5:
				name.setColor(Color.pink);
				secName.setColor(Color.pink);
				graphLine.setColor(Color.PINK);
				break;
			case 6:
				name.setColor(Color.YELLOW);
				secName.setColor(Color.YELLOW);
				graphLine.setColor(Color.YELLOW);
				break;
			}

		}

	}

	private double findGraphCoordinate(int rank) {							// with this I calculate starting coordinates for graphic lines
		double startingY = getHeight() - GRAPH_MARGIN_SIZE;					//with rank if rank is zero I make graphic lines down on the horizontal line
		double coordinate = 0;
		if (rank == 0) {
			coordinate = getHeight() - GRAPH_MARGIN_SIZE;
		} else {
			coordinate = (startingY - GRAPH_MARGIN_SIZE) * ((double) rank / MAX_RANK) + GRAPH_MARGIN_SIZE;
		}
		return coordinate;
	}

	private void drawBackground() {				//with this I draw background lines horizontal and vertical
		verticalLines();
		horizontalLines();
		writeDecades();
	}

	private void writeDecades() {													//I write decades with this method
		int plusDecade = 0;															 
		for (int i = 0; i <= getWidth() - lineGap; i += lineGap) {
			String finalDecade = Integer.toString(plusDecade * 10 + START_DECADE);	//this exact algorith was in seminarProblems
			GLabel decade = new GLabel(finalDecade);
			add(decade, i, getHeight());
			plusDecade++;
		}
	}

	private void horizontalLines() {								//draws Horizontal lines
		double bottomOffset = getHeight() - GRAPH_MARGIN_SIZE;
		GLine upperHorizontal = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(upperHorizontal);
		GLine lowerHorizontal = new GLine(0, bottomOffset, getWidth(), bottomOffset);
		add(lowerHorizontal);
	}

	private void verticalLines() {									////draws vertical lines
		lineGap = (double) (getWidth() / NDECADES);
		double endPoint = getHeight();
		for (double i = 0; i <= getWidth() - lineGap; i += lineGap) {
			GLine vertical = new GLine(i, 0, i, endPoint);
			add(vertical);
		}

	}

	public NameSurferGraph() {
		addComponentListener(this);
		nameList = new ArrayList<NameSurferEntry>();
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {				//clears name ArrayList
		nameList.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		nameList.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {								//this is for update canvas avter clicing clear
		removeAll();
		drawBackground();
		int nextColor = 0;
		for (int i = 0; i < nameList.size(); i++) {		//this is for choosing colors from switch loop.
			if (nextColor == 6) {
				nextColor = 0;
			}
			drawGraphic(nameList.get(i), nextColor);
			nextColor++;
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}
}
