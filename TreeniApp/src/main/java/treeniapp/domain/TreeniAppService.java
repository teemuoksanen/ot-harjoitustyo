
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
    * Method is used to check if the user is found and, if found, to set that user as logged in.
    *
    * @param   username   username of the user to be logged in
    *
    * @return <code>true</code> if username is found; <code>false</code> otherwise.
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
    * Method returns the user that is currently logged in.
    *
    * @return The <code>User</code> object for the user currently logged in.
    */
    public User getLoggedInUser() {
        return userLoggedIn;
    }
   
    /**
    * Method is used to log out any users by setting <code>userLoggedIn</code> as <code>null</code>.
    */
    public void logout() {
        userLoggedIn = null;
    }
   
    /**
    * Method is used to create a new user. 
    *
    * @param    username    username of the new user
    * @param    name        real name of the new user
    *
    * @return <code>false</code> if username is already in use; <code>true</code> if the new user is created.
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
    * Method to create a new workout. 
    *
    * @param    workout     <code>Workout</code> object for the new workout
    *
    * @return <code>true</code> if the workout was created.
    */
    public boolean newWorkout(Workout workout) {
        workoutDao.create(workout);
        return true;
    }
    
    /**
    * Method to list all workouts for the user who is currently logged in.
    * 
    * @return ArrayList of <code>Workout</code> objects for the user who is logged in. Empty list, if no user is logged in.
    */
    public List<Workout> getWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        if (userLoggedIn != null) {
            workouts = workoutDao.getAllByUser(userLoggedIn);
        }
        return workouts;
    }
    
    /**
    * Method to list all sport types.
    * 
    * @return ArrayList of <code>Sport</code> objects.
    */
    public List<Sport> getSports() {
        List<Sport> sports = new ArrayList<>();
        sports = sportDao.getAll();
        return sports;
    }
    
    /**
    * Method to get a sport by its id number.
    * 
    * @param    id     id number of the sport
    * 
    * @return <code>Sport</code> object.
    */
    public Sport getSportById(int id) {
        return sportDao.findById(id);
    }
    
}
