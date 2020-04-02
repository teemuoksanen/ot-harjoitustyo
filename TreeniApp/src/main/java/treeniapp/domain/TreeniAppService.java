
package treeniapp.domain;

import treeniapp.dao.WorkoutDao;
import treeniapp.dao.UserDao;

public class TreeniAppService {
    
    private WorkoutDao workoutDao;
    private UserDao userDao;
    private User userLoggedIn;
    
    public TreeniAppService(UserDao userDao) {
        this.workoutDao = null;
        this.userDao = userDao;
    }
    
    /**
    * Login
    */
    
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        
        if (user == null) {
            return false;
        }
        
        System.out.println("User '" + username + "' logged in."); //REMOVE
        userLoggedIn = user;
        
        return true;
    }
    
    /**
    * User currently logged in
    */   
    
    public User getLoggedInUser() {
        return userLoggedIn;
    }
   
    /**
    * Logout
    */  
    
    public void logout() {
        System.out.println("User '" + userLoggedIn.getUsername() + "' logged out."); //REMOVE
        userLoggedIn = null;
    }
   
    /**
    * Logout
    */  
    
    public boolean newUser(String username, String name) {
        System.out.print("Trying to create a new user '" + username + "'..."); //REMOVE
        
        // Check that there is no user with same username
        if (userDao.findByUsername(username) != null) {
            System.out.println("Failed - username already exists."); //REMOVE
            return false;
        }
        
        //Create a new user
        User newUser = new User(username, name);
        userDao.create(newUser);
        System.out.println("Succeeded."); //REMOVE
        return true;
    }
    
}
