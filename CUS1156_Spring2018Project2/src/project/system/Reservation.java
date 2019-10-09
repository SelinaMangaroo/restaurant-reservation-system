package project.system;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userId;
      private String restId;
      private LocalDate resDate;
      private int numGuests;
      
	public Reservation(String userId, String restId, LocalDate resDate, int numGuests) {
		this.userId= userId;
		this.restId = restId;
		this.resDate = resDate;
		this.numGuests = numGuests;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRestId() {
		return restId;
	}

	public void setRestId(String restId) {
		this.restId = restId;
	}

	public LocalDate getResDate() {
		return resDate;
	}

	public void setResDate(LocalDate resDate) {
		this.resDate = resDate;
	}

	public int getNumGuests() {
		return numGuests;
	}

	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}
      
	
      
}
