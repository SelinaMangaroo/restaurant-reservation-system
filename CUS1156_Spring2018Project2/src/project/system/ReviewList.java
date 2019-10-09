package project.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A list of reviews
 * @author Bonnie MacKellar
 *
 */
public class ReviewList implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Review> reviews = new ArrayList<Review>();

    
    /**
     * returns a list of reviews posted by a particular user
     * The list will be empty if there are no reviews
     * @param rid - the user id
     * @return a list of reviews
     */
    public List<Review> findByUser(String uid) {
    	ArrayList<Review> exportRevs = new ArrayList<Review>();
	    for (Review curr : reviews)
	    	if (curr.getUser().equals(uid))
	    		exportRevs.add(curr);
	    
	    return exportRevs;
	}

    
    /**
     * returns a list of reviews for a particular restaurant
     * The list will be empty if there are no reviews
     * @param rid - the restaurant id
     * @return a list of reviews
     */
    public List<Review> findByRestaurant(String rid) {
    	ArrayList<Review> exportRevs = new ArrayList<Review>();
	    for (Review curr : reviews)
	    	if (curr.getRestaurant().equals(rid))
	    		exportRevs.add(curr);
	    
	    return exportRevs;
	}
    

	/**
	 * add a review to the collection
	 * @param review
	 */
	public void add(Review review) {
		reviews.add(review);
		
	}


	/**
	 * this returns a copy of the collection of reviews
	 * it may be used for displaying information on reviews
	 * @return a list of users
	 */
	public List<Review> export()
	{
		ArrayList<Review> exportList = new ArrayList<Review>(reviews);
		return exportList;
	}
    		
}
