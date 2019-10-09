 package project.system;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class ReservationList implements Serializable{

	private static final long serialVersionUID = 1L;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation> ();
    
   
    
    public void add(Reservation res) {
    	reservations.add(res);
    }
    

    /**
     * this returns an iterator for the reservations on the list
     * @return
     */
    public Iterator<Reservation> iter() {
    	return reservations.iterator();
    }
    

    /**
     * this returns the number of reservations on the list
     * @return
     */
    public int size() {
    	return reservations.size();
    }
    
    /**
     * computes the total number of guests in the reservations on the list
     * @return total number of guests
     */
    public int numReserved() {
    	int totalGuests = 0;
    	for (Reservation curr : reservations) {
    		totalGuests = totalGuests + curr.getNumGuests();
    	}
    	
    	return totalGuests;
    }
    
    /**
     * this returns the reservation made on a particular date
     * @param requestedDate
     * @return
     */
    public Reservation findReservationsByDate(LocalDate requestedDate) {
		for (Reservation curr : reservations) {
			if (curr.getResDate().equals(requestedDate))
				return curr;
		}
		return null;
	}
	
    /**
     * returns a list of reservations made for a particular restauran
     * @param rid
     * @return
     */
	public ArrayList<Reservation> findReservationsByRestaurant(String rid) {
		ArrayList<Reservation> resByRest = new ArrayList<Reservation>();
		for (Reservation curr : reservations) {
			if (curr.getRestId().equals(rid))
				resByRest.add(curr);
		}
		return resByRest;
	}
 
}