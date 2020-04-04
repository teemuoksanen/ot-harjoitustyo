
package treeniapp.domain;

import java.util.ArrayList;
import java.util.List;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.dao.SportDao;

public class TreeniAppService {
    
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private SportDao sportDao;
    private User userLoggedIn;
    
    public TreeniAppService(UserDao userDao, WorkoutDao workoutDao, SportDao sportDao) {
        this.userDao = userDao;
        this.workoutDao = workoutDao;
        this.sportDao = sportDao;
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
    * Create User
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
    
    /**
    * Workouts
    */   
    
    public List<Workout> getWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        if (userLoggedIn != null) {
            System.out.println("Haetaan treenit: " + userLoggedIn.getUsername());
            workouts = workoutDao.getAllByUser(userLoggedIn);
        }
        return workouts;
    }
    
}
