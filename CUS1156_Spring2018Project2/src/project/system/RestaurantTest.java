package project.system;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class RestaurantTest {
    public Restaurant rest;
    public User user1;
    
    @Before
	public void setUp() throws Exception {
    	rest = new Restaurant("123", "Rest1", "Chinese", "111 First", "Manhattan", "NY",
    			"11111", true, 8);
  
    	user1 = new User("fred", "smith","s1");
	}

	@Test
	public void testMakeReservation() {
		Boolean success = rest.makeReservation(LocalDate.of(2018, 5, 15), user1, 5);
		assertTrue(success);
		Iterator<Reservation> iter = rest.reservationIteratorByDate(LocalDate.of(2018, 5, 15));
		Reservation res = iter.next();
		assertEquals("s1", res.getUserId());
		
	}
	
	@Test
	public void testMakeReservationTooMany() {
		rest.makeReservation(LocalDate.of(2018, 5, 15), user1, 5);
		
		// too many guests
		assertFalse(rest.makeReservation(LocalDate.of(2018,5,15),user1,5));
		
		// original reservation should still be on list
		Iterator<Reservation> iter = rest.reservationIteratorByDate(LocalDate.of(2018, 5, 15));
		Reservation res = iter.next();
		assertEquals("s1", res.getUserId());
	}

}
