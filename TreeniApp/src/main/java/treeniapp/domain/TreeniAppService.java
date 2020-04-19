
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
        userLoggedIn = null;
    }
   
    /**
    * Create User
    */  
    
    public boolean newUser(String username, String name) {
        
        // Check that there is no user with same username
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        
        //Create a new user
        User newUser = new User(username, name);
        userDao.create(newUser);
        return true;
    }
   
    /**
    * Create Workout
    */  
    
    public boolean newWorkout(Workout workout) {
        workoutDao.create(workout);
        return true;
    }
    
    /**
    * List workouts
    */   
    
    public List<Workout> getWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        if (userLoggedIn != null) {
            workouts = workoutDao.getAllByUser(userLoggedIn);
        }
        return workouts;
    }
    
    /**
    * List sports
    */   
    
    public List<Sport> getSports() {
        List<Sport> sports = new ArrayList<>();
        sports = sportDao.getAll();
        return sports;
    }
    
    /**
    * Get sport by ID
    */   
    
    public Sport getSportById(int id) {
        return sportDao.findById(id);
    }
    
}
