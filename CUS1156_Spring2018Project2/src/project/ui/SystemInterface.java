package project.ui;

import java.util.Scanner;

import project.system.ReservationSystem;

/**
 * superclass for menu based user interface classes
 * @author Bonnie MacKellar
 *
 */
public abstract class SystemInterface {
	protected Scanner in;
	protected ReservationSystem rsystem;
	
	/**
	 * create a SystemInterface object that collaborates
	 * with the ReservationSystem passed in as a parameter
	 * @param system
	 */
	   public SystemInterface(ReservationSystem system)
	   {
		  in = new Scanner(System.in);
	      rsystem = system;
	   }

	  /**
	   * this method handles all interaction with the user
	   * It repeatedly displays a menu of options, allows
	   * the customer to enter an option, and then displays results
	   * from processing the customer's choice
	   */
	   public abstract void run();
}
