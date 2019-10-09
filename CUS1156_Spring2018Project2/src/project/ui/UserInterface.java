package project.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import project.system.*;


public class UserInterface extends SystemInterface{
	
	/**
	 * create a UserInterface object that collaborates
	 * with the ReservationSystem passed in as a parameter
	 * @param system
	 */
	   public UserInterface(ReservationSystem system) {
	    super(system);
	   }
	   
	   /**
		   * this method handles all interaction with the user
		   * It repeatedly displays a menu of options, allows
		   * the customer to enter an option, and then displays results
		   * from processing the customer's choice
		   */
		   public void run()
		   {
			   System.out.println("Please enter your user id");
			   String userId = in.nextLine();
			   Boolean valid = rsystem.loginUser(userId);
			   

			   if (valid) {				   
			   String command = readCommand();
			      while (!command.equals("Q"))
			      {  
			         
			         if (command.equals("R")) 
			            makeAReservation();
			         
			         else
			         if (command.equals("S"))
			           searchForRestaurants();
			   
			     
			         else
			         if (command.equals("D"))
			        	 displayRestaurantInfoById();
			         
			         else 
			        	 if (command.equals("C"))
			        	   addReview();
			        	 else
			        		 if (command.equals("SR"))
			        			 showReviews();
			        		 else if (command.equals("AR"))
			        			 makeAReservation();
			        		 else if (command.equals("LR"))
			        			 listReservations();
			        	
			         else
			        	 System.out.println("Invalid command");
			         command = readCommand();
			      }
			      System.out.println("Bye!");
			   }
			   else
				   System.out.println("Invalid customer number");
			   
			   in.close();
			   }
		   
		   private void makeAReservation() {
			   Restaurant rest = selectRestaurant();
			     if (rest != null) {
			    	 System.out.println("Enter the date (use YYYY-MM-DD format)");
		    	 String dateString = in.nextLine();
		    	 LocalDate resDate = LocalDate.parse(dateString);
		    	 System.out.println("How many guests?");
		    	 String numGuestsStr= in.nextLine();
		    	 int numGuests = Integer.parseInt(numGuestsStr);
		        Boolean success = rsystem.makeReservation(rest, resDate, numGuests);
			     }
			     else
			    	 System.out.println("There is no restaurant with that id");
		
	}

		private void displayRestaurantInfoById() {
		     Restaurant rest = selectRestaurant();
		     if (rest != null) {
		    	 displayRestaurantInfo(rest);
		    	 
		     }
		     else
		    	 System.out.println("There is no restaurant with that id");
		
	}

		private Restaurant selectRestaurant() {
			System.out.println("Enter the restaurant id");
		     String id = in.nextLine();
		     Restaurant rest = rsystem.findRestaurant(id);
			return rest;
		}

		private void displayRestaurantInfo(Restaurant rest) {
			System.out.println();
		     System.out.println("--------- " + rest.getName() + " ----------");
		     System.out.println("Cuisine:" + rest.getCuisine());
		     System.out.println("Has liquor license: " + rest.getHasLiquorLicense());
		     System.out.println("Address");
		     System.out.println(rest.getStreetAddr());
		     System.out.println(rest.getCity() + ", " + rest.getState() + " " + rest.getZip());
		     System.out.println();
		     System.out.println("User Reviews");
		     displayReviews(rest.getRid());
		}
		   
		private void displayReviews(String id)
		{
			List<Review> reviews = rsystem.showReviewsByRestaurant(id);
			int count= 1;
			for (Review rev : reviews) {
				System.out.println("");
				System.out.println("REVIEW " + count);
				count++;
				System.out.println("Date " + rev.getTimePosted());
		
				System.out.println("User " + rev.getUser());
				displayStars(rev.getNumStars());
				System.out.println(rev.getReview());
			}
		}
		
		private void displayStars(int numStars) {
			System.out.print("Number of stars ");
			for (int i=0;i<numStars;i++)
				System.out.print("*");
			System.out.println();
		}

		private void showReviews() {
		      List<Review> reviews = rsystem.showReviewsByCurrentUser();
		      System.out.println("Your reviews: ");
		      for (Review curr: reviews)
		      {
		    	  System.out.println("------------------------------");
		    	  Restaurant rest = rsystem.findRestaurant(curr.getRestaurant());
		    	  System.out.println("Restaurant : " + rest.getName());
		    	  System.out.println("Date: " + curr.getTimePosted());
		    	    displayStars(curr.getNumStars());
		    	  System.out.println("Review: " + curr.getReview());
		    	  
		      }
		
	}

		private void addReview() {
		      System.out.println("Enter a restaurant id");
		      String id = in.nextLine();
		      System.out.println("Enter your review");
		      String review = in.nextLine();
		      System.out.println("Enter the number of stars");
		      String numStarsStr = in.nextLine();
		      int numStars = Integer.parseInt(numStarsStr);
		      rsystem.addReviewForCurrentUser(id, review, numStars);
		
	}

		private String readCommand() {
			   System.out.println( "Please enter a command" );
			   System.out.println("S: Search for restaurants");
			   System.out.println("D: Display a restaurant");
			   System.out.println("C: Add a review");
			   System.out.println("SR: Show my reviews");
			   System.out.println("AR: add a reservation");
			   System.out.println("LR: list my reservations");
			   System.out.println("Q: quit");
		       System.out.println(">>>>>>");     
		       String command = in.nextLine().toUpperCase();
		       return command;
	}

		private void listReservations() {
			System.out.println("Your reservations are ");
			System.out.println("-------------------------------");
		   Iterator<Reservation> resIter = rsystem.listReservationsByUser();
		   while (resIter.hasNext()) {
			   Reservation res = resIter.next();
			   Restaurant restaurant = rsystem.findRestaurant(res.getRestId());
			   System.out.println(restaurant.getName());
			   System.out.println(res.getResDate());
			   System.out.println("-------------------------------");
		   }
	}

		private void searchForRestaurants() {
		   String searchCriterion = displaySearchMenu();
		   while (!isValidCriterion(searchCriterion)) {
			  System.out.println("Not a valid search criterion");
			  searchCriterion = displaySearchMenu();
		}
		   System.out.println("Enter the search value");
		   String criteriaVal = in.nextLine();
		   Iterator<Restaurant> rList = rsystem.searchByCriteria(searchCriterion, criteriaVal);
		   displayRestaurants(rList);
		}
		 
		
		private String displaySearchMenu() {
		
			System.out.println("What would you like to search on? Choose one");
			System.out.println("cuisine, city,state, name");
			String searchCriteria = in.nextLine();
			return searchCriteria;
			
		}

		private Boolean isValidCriterion(String criterion) {
			return ((criterion.equals("cuisine")) || (criterion.equals("city"))
				|| (criterion.equals("state")) || (criterion.equals("name")));
		}
		


		private void displayRestaurants(Iterator<Restaurant> rlist) {
			if (!rlist.hasNext())
				System.out.println("There are no restaurants in this category");
			else
		    	while (rlist.hasNext())
				   displayRestaurantInfo(rlist.next());
			
		}

		
}
