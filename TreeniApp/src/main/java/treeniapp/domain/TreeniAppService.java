
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
        
        System.out.println("User '" + username + "' logged in.");
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
        System.out.println("User '" + userLoggedIn.getUsername() + "' logged out.");
        userLoggedIn = null;
    }
    
}
