
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private static final int NAME_LENGTH = 10;
	JLabel label;
	JTextField name;
	JButton graph;
	JButton clear;
	NameSurferGraph canvas;
	NameSurferDataBase newNames = new NameSurferDataBase("names-data.txt");
	

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		label = new JLabel("Name");				//adds label 
		add(label, SOUTH);
		
		name = new JTextField(NAME_LENGTH);		// add box to write name you want to search
		add(name, SOUTH);
		
		graph = new JButton("Graph");			// writes graph
		add(graph, SOUTH);
		
		clear = new JButton("Clear");			//clears all the graphs
		add(clear, SOUTH);
		addActionListeners();
		name.addActionListener(this);
		canvas = new NameSurferGraph();
		add(canvas);
	}
	

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == graph || (e.getSource() != null && e.getSource() != clear)) {	
			if(newNames.findEntry(name.getText()) != null) {
				NameSurferEntry entry = newNames.findEntry(name.getText());
				canvas.addEntry(entry);
				canvas.update();
			} else {
				println("This name was not popular");
			}
			name.setText("");
		} else if(e.getSource() == clear) {
			canvas.clear();
			name.setText("");
		}
	}

}
