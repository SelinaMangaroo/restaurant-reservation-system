package project.system;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ReservationSystemTest {
    ReservationSystem rsys;
    
	@Before
	public void setUp() throws Exception {
		RestaurantList testRestaurants = new RestaurantList();
		testRestaurants.add(new Restaurant("41576328","BO BO KITCHEN CHINESE RESTAURANT","Chinese","1601 CORTELYOU ROAD","BROOKLYN","NY","11226",false,11));
	    testRestaurants.add(new Restaurant("41153141","TANAKA","Japanese","222 EAST   51 STREET","MANHATTAN","NY","10022",false,6));
	    testRestaurants.add(new Restaurant("41072012","HIRAKI","Japanese","33 WEST 19TH STREET","MANHATTAN","NY","10022",false,10));
	    
	    UserList testUsers = new UserList();
	    testUsers.add(new User("Joe","Frank","joe11"));
	    testUsers.add(new User("Sarah","Smith","ssmith"));
	    
	    ReviewList testReviews = new ReviewList();
	    testReviews.add(new Review("yummy",4,"ssmith","41576328",LocalDateTime.now()));
	    testReviews.add(new Review("yummy",3,"ssmith","41153141",LocalDateTime.now()));
	    testReviews.add(new Review("sort of nice",3,"joe11","41153141",LocalDateTime.now()));
	    
	    rsys = new ReservationSystem(testUsers, testRestaurants, testReviews);
	}

	@Test
	public void testLoginUser() {
		assertTrue("valid user",rsys.loginUser("joe11"));
		assertFalse("invalid user", rsys.loginUser("fred"));
	}

	@Test
	public void testValidateRestaurant() {
		assertTrue("valid restaurant",rsys.validateRestaurant("41576328"));
		assertFalse("invalid restaurant", rsys.validateRestaurant("555"));
	}

	@Test
	public void testFindUser() {
		assertNull("invalid user",rsys.findUser("fred"));
		assertNotNull("valid user",rsys.findUser("ssmith"));
	}

	@Test
	public void testFindRestaurant() {
		assertNotNull("valid restaurant",rsys.findRestaurant("41576328"));
		assertNull("invalid restaurant", rsys.findRestaurant("555"));
	}

	@Test
	public void testAddReviewForCurrentUser() {
		   rsys.loginUser("ssmith");
		   rsys.addReviewForCurrentUser("41072012", "my fave", 5);
		   List<Review> reviews = rsys.showReviewsByCurrentUser();
		   assertEquals(3,reviews.size()); // not the most precise test
	}

	@Test
	public void testShowReviewsByCurrentUser() {
		rsys.loginUser("joe11");
		List<Review> reviews = rsys.showReviewsByCurrentUser();
		Review review = reviews.get(0);
		assertEquals(3,review.getNumStars());
		assertEquals("joe11", review.getUser());
		assertEquals("sort of nice", review.getReview());
		assertEquals("41153141", review.getRestaurant());
	}

	@Test
	public void testShowReviewsByCurrentRestaurant() {
		rsys.validateRestaurant("41576328");
		List<Review> reviews = rsys.showReviewsByCurrentRestaurant();
		Review review = reviews.get(0);
		assertEquals(4,review.getNumStars());
		assertEquals("ssmith", review.getUser());
		assertEquals("yummy", review.getReview());
		assertEquals("41576328", review.getRestaurant());
		
	}

	@Test
	public void testSearchByCityCriteria() {
		Iterator<Restaurant> results = rsys.searchByCriteria("city", "BROOKLYN");
		assertTrue(results.hasNext());
		Restaurant rest = results.next();
		assertEquals("BO BO KITCHEN CHINESE RESTAURANT",rest.getName());
		assertEquals("Chinese",rest.getCuisine());
		assertEquals(11,rest.getCapacity());
	}
	
	@Test
	public void testSearchByNameCriteria() {
		Iterator<Restaurant> results = rsys.searchByCriteria("name", "BO BO KITCHEN CHINESE RESTAURANT");
		assertTrue(results.hasNext());
		Restaurant rest = results.next();
		assertEquals("BO BO KITCHEN CHINESE RESTAURANT",rest.getName());
		assertEquals("Chinese",rest.getCuisine());
		assertEquals(11,rest.getCapacity());
		
	}
	
	@Test
	public void testSearchByNameCriteriaNotExist() {
		Iterator<Restaurant> results = rsys.searchByCriteria("name", "FRED");
		assertFalse(results.hasNext());
	}
	
	@Test
	public void testSearchByCuisineCriteria() {
		Iterator<Restaurant> results = rsys.searchByCriteria("cuisine", "Japanese");
		int numResults = 0;
		while (results.hasNext()) {
			results.next();
			numResults++;
		}
		assertEquals("search by cuisine",2, numResults);
	}

	@Test
	public void testNumberOfReviewsForCurrentRestaurant() {
		rsys.validateRestaurant("41576328");
		assertEquals(1,rsys.numberOfReviewsForCurrentRestaurant());
	}

	@Test
	public void testAverageScoreForCurrentRestaurant() {
		rsys.validateRestaurant("41576328");
		assertEquals(4,rsys.averageScoreForCurrentRestaurant(),0);
	}

	@Test
	public void testMakeReservation() {
		rsys.loginUser("ssmith");
		Restaurant rest = rsys.findRestaurant("41576328");
		Boolean success = rsys.makeReservation(rest, LocalDate.of(2018, 5, 15), 5);
		assertTrue(success);
		Iterator<Reservation> iter = rest.reservationIteratorByDate(LocalDate.of(2018, 5, 15));
		Reservation res = iter.next();
		assertEquals("ssmith", res.getUserId());
		assertEquals(5, res.getNumGuests());
	}
	
	@Test
	public void testListReservationsyUser() {
		rsys.loginUser("ssmith");
		Restaurant rest1 = rsys.findRestaurant("41576328");
		rsys.makeReservation(rest1, LocalDate.of(2018, 5, 15), 5);
		Restaurant rest2 = rsys.findRestaurant("41153141");
		rsys.makeReservation(rest2, LocalDate.of(2018, 5, 16), 5);
		Iterator<Reservation> iter = rsys.listReservationsByUser();
		assertTrue(iter.hasNext());
		Reservation res = iter.next();
		assertEquals("41576328", res.getRestId());
		assertEquals(LocalDate.of(2018, 5, 15), res.getResDate());
		assertEquals(5, res.getNumGuests());
		res = iter.next();
		assertEquals("41153141", res.getRestId());
		assertEquals(LocalDate.of(2018, 5, 16), res.getResDate());
		assertEquals(5, res.getNumGuests());
		}
	
}
