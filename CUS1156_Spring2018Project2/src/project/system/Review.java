package project.system;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * One review. The data includes restaurant id, user id, number of stars, and the text of 
 * the review.
 * @author Bonnie MacKellar
 *
 */
public class Review implements Serializable{

	private static final long serialVersionUID = 1L;
private String review;
   private String uid;
   private String rid;
   private int numStars;
   private LocalDateTime timePosted;
   
   
   /**
    * creates a review from values passed in
    */
public Review(String review, int numStars, String uid, String rid, LocalDateTime timePosted) {
	super();
	this.review = review;
	this.numStars = numStars;
	this.uid = uid;
	this.rid = rid;
	this.timePosted = timePosted;
}


public String getReview() {
	return review;
}


public void setReview(String review) {
	this.review = review;
}


public String getUser() {
	return uid;
}


public void setUser(String uid) {
	this.uid = uid;
}


public String getRestaurant() {
	return rid;
}


public void setRestaurant(String rid) {
	this.rid = rid;
}


public LocalDateTime getTimePosted() {
	return timePosted;
}


public void setTimePosted(LocalDateTime timePosted) {
	this.timePosted = timePosted;
}


public int getNumStars() {
	return numStars;
}


public void setNumStars(int numStars) {
	this.numStars = numStars;
}
   
 
   
}
