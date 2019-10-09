package project.system;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * One user. The data includes an id, first name, and last name
 * Each user also has a list of reservations that he or she has made
 * @author Bonnie MacKellar
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fname;
	private String lname;
	private String uid;
	
    private ReservationList reservations = new ReservationList();
    
    /** 
	 * create a user from passed in values
	 */
	public User(String fname, String lname, String uid) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.uid = uid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void addReservation(Reservation res) {

			reservations.add(res);

	}

	/**
	 * returns a reservation made on a particular date
	 * @param requestedDate
	 * @return
	 */
	public Reservation findReservationsByDate(LocalDate requestedDate) {

		return reservations.findReservationsByDate(requestedDate);
	}
	
	/**
	 * return an iterator for the reservations belonging to this user
	 * @return an iterator
	 */
	public Iterator<Reservation> findAllReservations() {
		return reservations.iter();
	}
	
	/**
	 * returns an iterator for the collection of reservations for a particular restaurant
	 * @param rid
	 * @return
	 */
	public Iterator<Reservation> findReservationsByRestaurant(String rid) {
		ArrayList<Reservation> resByRest = new ArrayList<Reservation> ();
//		for (Reservation curr : reservations) {
//			if (curr.getRestId().equals(rid))
//				return curr;
//		}
//		return null;
		return reservations.findReservationsByRestaurant(rid).iterator();
	}
}
