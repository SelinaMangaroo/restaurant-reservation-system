package project.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * The dispatch class for our restaurant review and reservation system
 * interacts with the various collections of users, restaurants, review,
 * and eventually reservations to provide review and reservation behavior
 * @author Bonnie MacKellar
 *
 */
public class ReservationSystem implements Serializable {

	private static final long serialVersionUID = 1L;
	private UserList userList;
	private ReservationList reservations;
	private RestaurantList restList;
    private ReviewList reviews;
    private String currentId;

	/**
	 * create the reservation system, passing it lists of users, restaurants, and reservations
	 * 
	 * @param users
	 * @param restList
	 * @param reservations
	 */
	public ReservationSystem(UserList users, RestaurantList restList, ReviewList reviews ) {
		this.userList = users;
		this.reservations = reservations;
		this.restList = restList;
		this.reviews = reviews;
		
	}


	
	/**
	 * check if a user id is in the list of registered users and if so,
	 * set as the currently logged in user
	 * @param uid
	 * @return true if the user id is in the user list, false otherwise
	 */
	public Boolean loginUser(String uid) {
		User user = userList.find(uid);
		 if (user != null) {
		    	currentId = uid;
		    	return true;
		    }
		 return false;
	}
	
	/**
	 * check if a restaurant id is in the list of restaurants
	 * @param rid
	 * @return true if the restaurant id is in the restaurant list, false otherwise
	 */
	public Boolean validateRestaurant(String rid) {
		Restaurant rest = restList.find(rid);
		 if (rest != null) {
		    	currentId = rid;
		    	return true;
		    }
		 return false;
	  
	    
        
	}

	/**
	 * returns the first User object with matching id, or null otherwise
	 * @param uid
	 * @return matching User or null if not found
	 */
	public User findUser(String uid) {
		return userList.find(uid);
	}
	
	/**
	 * returns the first Restaurant object with matching id, or null otherwise
	 * @param rid
	 * @return matching Restaurant or null if not found
	 */
	public Restaurant findRestaurant(String rid) {
		return restList.find(rid);
	}
	
	
	/**
	 * Add a review for a restaurant for the currently logged in user
	 * @param id - restaurant id
	 * @param reviewTxt - the main text of the review
	 * @param numStars
	 */
	public void addReviewForCurrentUser(String rid, String review, int numStars) {
		addReview(currentId, rid, review, numStars);
		
	}

	/**
	 * Add a review for a restaurant
	 * @param user - user id
	 * @param id - restaurant id
	 * @param reviewTxt - the main text of the review
	 * @param numStars
	 */
	public void addReview(String uid, String rid, String reviewTxt, int numStars) {
		Restaurant rest = restList.find(rid);
		Review review = new Review(reviewTxt, numStars, uid, rest.getRid(), LocalDateTime.now());
		reviews.add(review);
		
	}

	public List<Review> showReviewsByCurrentUser() {
          return showReviewsByUser(currentId);
	}

	/**
	 * returns a list of all reviews posted by a user
	 * @param uid
	 * @return a list of reviews which may be empty
	 */
	public List<Review> showReviewsByUser(String uid) {
	      return reviews.findByUser(uid);
	}

	public List<Review> showReviewsByCurrentRestaurant() {
       return showReviewsByRestaurant(currentId);
	}

	/**
	 * returns a list of all reviews posted for a restaurant
	 * @param rid - restaurant id
	 * @return a list of reviews which may be empty
	 */
	public List<Review> showReviewsByRestaurant(String rid) {
		return reviews.findByRestaurant(rid);
	}

	/**
	 * returns a list of restaurants that match a value for any of the following
	 * criteria: cuisine, city, state, or restaurant name
	 * @param searchCriteria
	 * @param criteriaVal
	 * @return
	 */
	public Iterator<Restaurant> searchByCriteria(String searchCriteria, String criteriaVal) {

		if (searchCriteria.equalsIgnoreCase("cuisine"))
			   return restList.searchByCuisine(criteriaVal);
		   else if (searchCriteria.equals("city"))
			   return restList.searchByCity(criteriaVal);
		   else if (searchCriteria.equals("state"))
			   return restList.searchByState(criteriaVal);
		   else if (searchCriteria.equals("name"))
			   return restList.searchByName(criteriaVal);
          return Collections.emptyIterator();
	}

	/**
	 * returns the number of reviews posted for the current restaurant
	 * @return
	 */
   public int numberOfReviewsForCurrentRestaurant() {
	   List<Review> reviews = showReviewsByRestaurant(currentId);
	   return reviews.size();
   }


/**
 * returns the average number of stars on reviews for the current restaurant
 * @return
 */
public double averageScoreForCurrentRestaurant() {
	List<Review> reviews = showReviewsByRestaurant(currentId);
	int totalScore = 0;
	 for (Review curr: reviews) {
		 totalScore = totalScore + curr.getNumStars();
	 }
	return (double) totalScore/reviews.size(); 
}



public Boolean makeReservation(Restaurant rest, LocalDate resDate, int numGuests) {
	User user = findUser(currentId);
	Boolean success = rest.makeReservation(resDate, user, numGuests);
    return success;
}

/**
 * get the reservations for a user
 * @return reservations for a user or an empty iterator
 */
public Iterator<Reservation> listReservationsByUser(){
	User user = findUser(currentId);
	if (user != null) {
		return user.findAllReservations();
	}
	return Collections.emptyIterator();
}


	
public Iterator<Reservation> showReservationsByDateCurrentRestaurant(LocalDate date) {
	Restaurant rest = findRestaurant(currentId);
	if (rest != null) {
		return rest.reservationIteratorByDate(date);
	}
	return null;
}
}
