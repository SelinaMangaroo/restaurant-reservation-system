package project.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A list of users
 * @author Bonnie MacKellar
 */
public class UserList implements Serializable{

	private static final long serialVersionUID = 1L;

    private HashMap<String, User> users = new HashMap<String, User>();
    
    /**
     * returns the user object by user id, or null if not found
     * @param uid
     * @return
     */
	public User find(String uid) {

		return users.get(uid);
	}

	/**
	 * add a user to the collection. The key is the user id
	 * @param user
	 */
	public void add(User user) {
		users.put(user.getUid(), user);
		
	}

	/**
	 * this returns a copy of the collection of users
	 * it may be used for displaying information on users
	 * @return a list of users
	 */
	public List<User> export()
	{

		List<User> exportList = new ArrayList<User>(users.values());
		return exportList;
	}
	
	
}
