package project.system;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * One restaurant. The data includes restaurant id, name, cuisine, street address
 * city, state, zip code, and whether it has a liquor license. It also contains the capacity
 * of the restaurant - the number of patrons that can be accomodated at the same time
 * Finally, it contains all of the reservations for the restaurant, grouped by date.
 * @author Bonnie MacKellar
 *
 */
public class Restaurant implements Serializable{
  
	private static final long serialVersionUID = 1L;
	private String rid;
    private String name;
    private String cuisine;
    private String streetAddr;
    private String city;
    private String state;
    private String zip;
    private Boolean hasLiquorLicense;
    private int capacity;  // capacity 
    private HashMap<LocalDate, ReservationList> reservationsByDate = new HashMap<LocalDate, ReservationList>();

    /**
     * creates a restaurant from values passed in
     */
	public Restaurant(String rid, String name, String cuisine, String streetAddr, String city, String state, String zip,
			Boolean hasLiquorLicense, int capacity) {
		this.rid = rid;
		this.name = name;
		this.cuisine = cuisine;
		this.streetAddr = streetAddr;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.hasLiquorLicense = hasLiquorLicense;
		this.capacity = capacity;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getStreetAddr() {
		return streetAddr;
	}

	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Boolean getHasLiquorLicense() {
		return hasLiquorLicense;
	}

	public void setHasLiquorLicense(Boolean hasLiquorLicense) {
		this.hasLiquorLicense = hasLiquorLicense;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * create a reservation for a user on a given date
	 * this will be added to the reservation list for that date
	 * if there were no reservations for that date, an entry
	 * will be created
	 * This method will also make sure that the reservation is added
	 * in the User class as well
	 * @param resDate
	 * @param user
	 * @param numGuests
	 * @return
	 */
	public Boolean makeReservation(LocalDate resDate, User user, int numGuests) {
		ReservationList resList = findReservationsByDate(resDate);
		if (resList == null) {// no reservations have been made for this date
			resList = new ReservationList();
			reservationsByDate.put(resDate,resList);
		}
		if (resList.numReserved() + numGuests <= capacity) {
			Reservation res = new Reservation( user.getUid(), this.getRid(), resDate, numGuests);
			resList.add(res);
			// make sure the user adds the reservation too
			user.addReservation(res);
			return true;
		}
	   return false;
	}

	/**
	 * returns an iterator that gives access to reservations on a particular date
	 * @param requestedDate
	 * @return
	 */
	public Iterator<Reservation> reservationIteratorByDate(LocalDate requestedDate){
		ReservationList reservations = findReservationsByDate(requestedDate);
		if (reservations ==null) {
			return Collections.emptyIterator();
		}
		else
			return reservations.iter();
	}
	
	private  ReservationList findReservationsByDate(LocalDate requestedDate) {
		return reservationsByDate.get(requestedDate);
	}

	
	
}
