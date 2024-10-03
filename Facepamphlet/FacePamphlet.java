
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import java.awt.event.ActionEvent;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.program.ConsoleProgram;
import acm.program.Program;
import acmx.export.javax.swing.JButton;
import acmx.export.javax.swing.JLabel;
import acmx.export.javax.swing.JTextField;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	private JLabel name;
	private JTextField nameInput;
	private JButton add;
	private JButton delete;
	private JButton lookUp;
	private JTextField status;
	private JButton changeStatus;
	private JTextField pictureAdress;
	private JButton changePicture;
	private JTextField friendName;
	private JButton addFriend;
	private String consoleMessage = "";
	FacePamphletProfile currProfile;
	FacePamphletDatabase profileInfo;
	FacePamphletCanvas newCanvas = new FacePamphletCanvas();
	/*
	 * everything there are buttons textfields and labels on canvas to use our facePamphlet
	 * also listeners
	 */
	public void init() {
		profileInfo = new FacePamphletDatabase();
		name = new JLabel("Name");
		add(name, NORTH);
		nameInput = new JTextField(TEXT_FIELD_SIZE);
		add(nameInput, NORTH);
		nameInput.addActionListener(this);
		add = new JButton("Add");
		add(add, NORTH);
		add.addActionListener(this);
		delete = new JButton("Delete");
		add(delete, NORTH);
		delete.addActionListener(this);
		lookUp = new JButton("Lookup");
		add(lookUp, NORTH);
		lookUp.addActionListener(this);
		status = new JTextField(TEXT_FIELD_SIZE);
		add(status, WEST);
		changeStatus = new JButton("Change Status");
		add(changeStatus, WEST);
		changeStatus.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		pictureAdress = new JTextField(TEXT_FIELD_SIZE);
		add(pictureAdress, WEST);
		changePicture = new JButton("Change Picture");
		add(changePicture, WEST);
		changePicture.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		friendName = new JTextField(TEXT_FIELD_SIZE);
		add(friendName, WEST);
		friendName.addActionListener(this);
		addFriend = new JButton("Add Friend");
		add(addFriend, WEST);
		addFriend.addActionListener(this);
		addActionListeners();
		add(newCanvas);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == add || e.getSource() == nameInput) {				//with add button profile is created if doesn't exist and if exist summons it
			if (profileInfo.containsProfile(nameInput.getText())) {
				currProfile = profileInfo.getProfile(nameInput.getText());
				consoleMessage = "This user already exists " + nameInput.getText();
			} else {
				FacePamphletProfile newUser = new FacePamphletProfile(nameInput.getText());
				profileInfo.addProfile(newUser);
				currProfile = newUser;
				consoleMessage = "";
			}
		} else if (e.getSource() == delete || e.getSource() == nameInput) {		//it deletes profile and if dont exist messages it 
			currProfile = null;
			if (profileInfo.containsProfile(nameInput.getText())) {
				profileInfo.deleteProfile(nameInput.getText());
				currProfile = null;
				consoleMessage = "This user: " + nameInput.getText() + " was deleted";
			}
		} else if (e.getSource() == lookUp || e.getSource() == nameInput) {			//with this button it searches existed account
			if (profileInfo.containsProfile(nameInput.getText())) {
				FacePamphletProfile profile = profileInfo.getProfile(nameInput.getText());
				currProfile = profile;
				consoleMessage = "";
			}
			if (!profileInfo.containsProfile(nameInput.getText())) {
				currProfile = null;
				consoleMessage = "Profile with this kind of name doesn't exist ";
			}
		} else if (e.getSource() == changeStatus || e.getSource() == status) {			//it changes status
			currProfile.setStatus(status.getText());
			consoleMessage = "status changes";
		} else if (e.getSource() == changePicture || e.getSource() == pictureAdress) {	//this button changes photo
			GImage img = null;
			img = new GImage(pictureAdress.getText());
			currProfile.setImage(img);
			consoleMessage = "";
		} else if (e.getSource() == addFriend || e.getSource() == friendName) {			//this  button adds friend if existed
			if (profileInfo.containsProfile(friendName.getText())) {
				;
				currProfile.addFriend(friendName.getText());
				consoleMessage = "";
				profileInfo.getProfile(friendName.getText()).addFriend(currProfile.getName());
			} else {
				consoleMessage = "This kind of user " + friendName.getText() + " doesn't exist";
			}
		}

		newCanvas.displayProfile(currProfile);
		newCanvas.showMessage(consoleMessage);
	}

}
