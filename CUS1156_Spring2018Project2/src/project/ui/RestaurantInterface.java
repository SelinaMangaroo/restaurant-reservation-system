package project.ui;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import project.system.ReservationSystem;
import project.system.Restaurant;
import project.system.Review;
import project.system.User;
import project.system.Reservation;

public class RestaurantInterface extends SystemInterface{

	/**
	 * create a RestaurantInterface object that collaborates
	 * with the ReservationSystem passed in as a parameter
	 * @param system
	 */
	   public RestaurantInterface(ReservationSystem system){
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
			  // in = new Scanner(System.in);
			   System.out.println("Please enter your restaurant id");
			   
			   String restNum = in.nextLine();
			   Boolean valid = rsystem.validateRestaurant(restNum);

		
			   if (valid) {
			   String command = readCommand();
			      while (!command.equals("Q"))
			      {  		         
			         if (command.equals("SR"))
			         {  
			            showReviews();
			         }
			         else
			        	 if(command.equals("S"))
			        	 {
			        		 showReviewStatistics();
			        	 }
			        	 else if (command.equals("RD")) {
			        		 showReservationsByDate();
			        	 }
			        	 else if (command.equals("R")) {
			        		 showAllReservations();
			        	 }

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
		   
		   private void showAllReservations() {
		// TODO Auto-generated method stub
		
	}

		private void showReservationsByDate() {
			   System.out.println("Enter the date (use YYYY-MM-DD format)");
		    	 String dateString = in.nextLine();
		    	 LocalDate resDate = LocalDate.parse(dateString);
		       Iterator<Reservation> reservations = rsystem.showReservationsByDateCurrentRestaurant(resDate);
		       if (reservations == null) {
		    	   System.out.println("Restaurant does not exist");
		    	   return;
		       }
		       if (!reservations.hasNext()) {
		    	   System.out.println("There are no reservations for this date");
		    	   return;
		       }
		       int count = 1;
		       while (reservations.hasNext()) {
                   System.out.println("Reservation " + count);
                   count = count + 1;
		    	   Reservation res = reservations.next();
		    	   User user = rsystem.findUser(res.getUserId());
		    	   System.out.println(user.getFname() + " " + user.getLname() + " " + user.getUid());
		    	   }
		
	}

		private void showReviews() {
			   List<Review> reviews = rsystem.showReviewsByCurrentRestaurant();
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
		   
		   
		   
		   private void showReviewStatistics() {
			   
				 List<Review> reviews = rsystem.showReviewsByCurrentRestaurant();
				 
				 int numReviews = rsystem.numberOfReviewsForCurrentRestaurant();
			      System.out.println("There are " + numReviews + " reviews");
				 double averageScore = rsystem.averageScoreForCurrentRestaurant();
				 System.out.println("The average score is " + averageScore);
				 
			 }

		private String readCommand() {
			   System.out.println( "Please enter a command" );
			   System.out.println("SR: show my reviews");
			   System.out.println("S: show review statistics");
			   System.out.println("RD: show reservations by date");
			   System.out.println("R: show all reservations");
			   System.out.println("Q: quit");
		       System.out.println(">>>>>>");     
		       String command = in.nextLine().toUpperCase();
		       return command;
	}
		  
}
