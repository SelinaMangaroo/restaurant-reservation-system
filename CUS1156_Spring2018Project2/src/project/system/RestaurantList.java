package project.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list of restaurants
 * 
 * @author Bonnie MacKellar
 *
 */
public class RestaurantList implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

		public Restaurant find(String rid) {
		    for (Restaurant curr : restaurants)
		    	if (curr.getRid().equals(rid))
		    		return curr;
		    
		    return null;
		}


		/**
		 * add a restaurant to the collection
		 * @param rest - a restaurant
		 */
		public void add(Restaurant rest) {
			restaurants.add(rest);
			
		}


		/**
		 * this returns a copy of the collection of users
		 * it may be used for displaying information on users
		 * @return a list of users
		 */
		public Iterator<Restaurant> export()
		{
			ArrayList<Restaurant> exportList = new ArrayList<Restaurant>(restaurants);
			return exportList.iterator();
		}


		/**
		 * returns a list of restaurant objects that match the cuisine
		 * If there are none, the list will be empty
		 * @param cuisine
		 * @return list of restaurants
		 */
		public Iterator<Restaurant> searchByCuisine(String cuisine) {
			ArrayList<Restaurant> exportList = new ArrayList<Restaurant>();
			for (Restaurant curr: restaurants) {
				if (curr.getCuisine().equals(cuisine))
					exportList.add(curr);
			}
			return exportList.iterator();
		}
		
		/**
		 * returns a list of restaurant objects that match the restaurant name
		 * If there are none, the list will be empty
		 * @param name
		 * @return list of restaurants
		 */
		public Iterator<Restaurant> searchByName(String name) {
			ArrayList<Restaurant> exportList = new ArrayList<Restaurant>();
			for (Restaurant curr: restaurants) {
				if (curr.getName().equals(name))
					exportList.add(curr);
			}
			return exportList.iterator();
		}
		
		/**
		 * returns a list of restaurant objects that match the city
		 * If there are none, the list will be empty
		 * @param city
		 * @return list of restaurants
		 */
		public Iterator<Restaurant> searchByCity(String city) {
			ArrayList<Restaurant> exportList = new ArrayList<Restaurant>();
			for (Restaurant curr: restaurants) {
				if (curr.getCity().equals(city))
					exportList.add(curr);
			}
			return exportList.iterator();
		}

		/**
		 * returns a list of restaurant objects that match the state
		 * If there are none, the list will be empty
		 * @param state
		 * @return list of restaurants
		 */
		public Iterator<Restaurant> searchByState(String state) {
			ArrayList<Restaurant> exportList = new ArrayList<Restaurant>();
			for (Restaurant curr: restaurants) {
				if (curr.getState().equals(state))
					exportList.add(curr);
			}
			return exportList.iterator();
		}


	
}
