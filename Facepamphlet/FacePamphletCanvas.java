/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	private GLabel message;
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {				//creates message change font and color for to use on canvas 
		if(message != null) {
			remove(message);
		}
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		message.setLocation(getWidth() / 2 - message.getWidth() / 2 , getHeight() - BOTTOM_MESSAGE_MARGIN);
		add(message);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	private void createBorder() {								//creates border for picture
		GRect border = new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
		add(border, LEFT_MARGIN , TOP_MARGIN + IMAGE_MARGIN);
		GLabel noImg = new GLabel("No Image");					//also add message no image if image isnot added yet
		noImg.setFont(PROFILE_IMAGE_FONT);
		noImg.setColor(Color.black);
		noImg.setLocation(LEFT_MARGIN + noImg.getWidth() / 2 , TOP_MARGIN + IMAGE_MARGIN
				+ IMAGE_HEIGHT / 2);
	}
	private void addPicture(GImage image) {							//adds picture
		image.setLocation(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
		image.setSize(IMAGE_WIDTH , IMAGE_HEIGHT);
		add(image);
	}
	private void addName(String name) {								//adds name at the top of image
		GLabel userName = new GLabel(name);
		userName.setFont(PROFILE_NAME_FONT);
		userName.setColor(Color.blue);
		userName.setLocation(LEFT_MARGIN,TOP_MARGIN);
		add(userName);	
	}
	
	private void addStatus(String status) {							// adds status at the bottom of the photo
		String newStatus = "";
		if(status.isEmpty()) {
			newStatus = "No current Status";
		}else {
			newStatus = status;
		}	
		GLabel userStatus = new GLabel(newStatus);
		userStatus.setFont(PROFILE_STATUS_FONT);
		userStatus.setColor(Color.black);
		userStatus.setLocation(LEFT_MARGIN , TOP_MARGIN + IMAGE_MARGIN + STATUS_MARGIN + IMAGE_HEIGHT);
		add(userStatus);
		
	}
	private void addFriends(Iterator<String> friendListIterator) {		//adds friends on the friend list
		GLabel friends = new GLabel("Friends:");
		friends.setColor(Color.black);
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth() / 2 , TOP_MARGIN);
		int countForHeight = 1;
		while(friendListIterator.hasNext()) {
			String newFriend = friendListIterator.next();
			GLabel friendList = new GLabel(newFriend);
			friendList.setFont(PROFILE_FRIEND_FONT);
			friendList.setLocation(getWidth() / 2, TOP_MARGIN + countForHeight * friendList.getHeight() );
			countForHeight++;
			add(friendList);
		}
	}
	public void displayProfile(FacePamphletProfile profile) {		//all the methods from up there is summoned here
		removeAll();
		if(profile != null) {
			addName(profile.getName());
			if(profile.getImage() != null) {
				addPicture(profile.getImage());
			} else {
				createBorder();
			}
			addStatus(profile.getStatus());
			addFriends(profile.getFriends());
		} 
		
	}

	
}
