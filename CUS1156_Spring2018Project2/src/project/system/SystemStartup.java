package project.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import project.ui.*;

/**
 * Starts up the restaurant review and reservation application
 * It loads data files 
 * and then starts the user or restaurant interface
 * NOTE: the startup code expects its data files at the top level of the
 * Eclipse project. The first time it is run, it will look for
 * two text files: restaurants.txt and users.txt. It will read the restaurants and users 
 * from these files, and will create an empty list of reviews and empty list
 * of reservations.
 * At the end of any session, including the first session, it will serialize the internal
 * data structure, including reservations, reviews, restaurants, and users, to rsys.txt.
 * Unlike the text files, you will not be able to read this rsys.txt file, but your program can.
 * The logic in system startup detects the presence of this file and reads from it
 * instead of restaurants.txt and users.txt.
 * On occassion, if you have made a lot of changes to the source files, rsys.txt
 * will go out of sync with your program. If that happens, you will see an error message right
 * at startup saying it can't read this file. In that case, delete rsys.txt and rerun
 * your program. It will start from scratch again and will rewrite rsys.txt. Unfortunately,
 * you will lose any reviews and reservations you have built up to that point.
 * @author Bonnie MacKellar
 *
 */
public class SystemStartup {

	public static void main(String[] args) {
		ReservationSystem resSystem;
	
    	Scanner in = new Scanner(System.in);
    	
    	// if the serialized file exists, use it
           try {
			if (serializedFileExists("rsys.txt")){
				resSystem = loadReservationSystem();
				
			} else{
				// otherwise, this is the first time running
				// this program so just load the restaurants
				// and users from the text files
				RestaurantList restList = loadRestaurants();
				UserList userList = loadUsers();
				resSystem = new ReservationSystem(userList, restList, new ReviewList());
			} 
			SystemInterface ui =  createUI(resSystem, in);
			ui.run();
			in.close();
			// serialize the entire data structure for the next time.
			writeResSystem(resSystem);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static Boolean serializedFileExists(String filePath) {
		File tmpDir = new File(filePath);
		return tmpDir.exists();
	}
	private static void writeResSystem(ReservationSystem rsys) throws Exception, IOException {
		ObjectOutputStream out=null;
		out = new ObjectOutputStream( new FileOutputStream("rsys.txt"));
		out.writeObject(rsys); 
		out.close();
	}

	private static ReservationSystem loadReservationSystem() throws IOException, ClassNotFoundException {
		ReservationSystem resSystem=null;
			FileInputStream fin = new FileInputStream("rsys.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);
			resSystem = (ReservationSystem) oin.readObject();

		return resSystem;
	}

	private static SystemInterface createUI(ReservationSystem resSystem, Scanner in) {
		SystemInterface ui = null;
		System.out.println("What type of user are you? Type user or restaurant");

	    String type = in.nextLine();
	    
	    if (type.equals("user")){
	        ui = new UserInterface(resSystem);        
	    }
	    else{
	    	ui = new RestaurantInterface(resSystem);	        
	    }
		return ui;
	}

	private static ReviewList loadReviews() {
		ReviewList revs = new ReviewList();
		Scanner fileIn;
	    try {
			fileIn = new Scanner(new File("reviews.txt"));	
		
		while (fileIn.hasNextLine()){		 
		 String line = fileIn.nextLine();		 
		 Review rev = processReviewInputLine(line);
		 
		 revs.add(rev);		 
     	}
		fileIn.close();
	    } catch (FileNotFoundException e) {
			System.err.println("Error reading review file");
			e.printStackTrace();
		}
		
		return revs;
	}

	private static Review processReviewInputLine(String line) {
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(",");
		String uid = lineScanner.next();

		String rid = lineScanner.next();
		String revTxt = lineScanner.next();
		int numStars = lineScanner.nextInt();
		String dateStr = lineScanner.next();

	    LocalDateTime dateOfReview = LocalDateTime.now();
		 try {
				DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
				dateOfReview = LocalDateTime.parse(dateStr, formatter);
				
			} catch (DateTimeParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 Review rev = new Review(revTxt, numStars,uid, rid, dateOfReview);
		 return rev;
	}

	private static void writeReviews(ReviewList reviews) {
		FileWriter outFile;
		
		try {
			outFile = new FileWriter(new File("reviews.txt"));
			 List<Review> export = reviews.export();
			 String newline = System.getProperty("line.separator");
			 for (Review curr : export)
				 outFile.write(curr.getUser() + "," + curr.getRestaurant() + "," + curr.getReview() 
				    + "," + curr.getNumStars() + "," + curr.getTimePosted() + newline);
			 
			 outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static RestaurantList loadRestaurants() {
		RestaurantList restList = new RestaurantList();
		Scanner fileIn;
	    try {
			fileIn = new Scanner(new File("restaurants.txt"));
		
		while (fileIn.hasNextLine())
	    {
		 
		 String line = fileIn.nextLine();
		 
		 Restaurant rest = processRestInputLine(line);
		 System.out.println("restaurant id " + rest.getRid());
		 restList.add(rest);
   	}
		fileIn.close();
	    } catch (FileNotFoundException e) {

			 System.err.println("Error reading restaurant file");
			e.printStackTrace();
		}
		
		return restList;
	}

	private static Restaurant processRestInputLine(String line) {
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(",");
		String id = lineScanner.next();
		String name = lineScanner.next();
		String cuisine = lineScanner.next();
		String addr1 = lineScanner.next();
		String city = lineScanner.next();
		String state = lineScanner.next();
		String zip = lineScanner.next();
		char liqFlag = lineScanner.next().charAt(0);
		Boolean hasLic;
		if (liqFlag == 'T')
			hasLic=true;
		else
			hasLic = false;
		int capacity = lineScanner.nextInt();
		
	    Restaurant rest = new Restaurant(id,name, cuisine, addr1,city,state,zip, hasLic, capacity);
		 		 
		 lineScanner.close();
		return rest;
	}

	

	private static User processUserInputLine(String line) {
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(",");
		String id = lineScanner.next();
		String fname = lineScanner.next();	 
		String lname = lineScanner.next();

	    User user = new User(fname,lname,id);
		 		 
		 lineScanner.close();
		return user;
	}
	
	private static UserList loadUsers() {
		UserList userList = new UserList();
		Scanner fileIn;
	    try {
			fileIn = new Scanner(new File("users.txt"));
		
		
		while (fileIn.hasNextLine())
	    {
		 
		 String line = fileIn.nextLine();
		 
		 User user = processUserInputLine(line);
		 
		 userList.add(user);
   	}
		fileIn.close();
	    } catch (FileNotFoundException e) {
		    System.err.println("error reading user file");
			e.printStackTrace();
		}
		
		return userList;
	}


	}


