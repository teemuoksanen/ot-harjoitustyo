
package treeniapp.domain;

import java.util.ArrayList;
import java.util.List;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.dao.SportDao;

/**
 * Class handles the application logic.
 */
public class TreeniAppService {
    
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private SportDao sportDao;
    private User userLoggedIn;
    private Formatter format;
    
    /**
    * Constructor for <code>TreeniAppService</code> with the application DAOs injected.
    *
    * @param userDao       Class implementing <code>UserDao</code> interface.
    * @param workoutDao    Class implementing <code>WorkoutDao</code> interface.
    * @param sportDao      Class implementing <code>SportDao</code> interface.
    */
    public TreeniAppService(UserDao userDao, WorkoutDao workoutDao, SportDao sportDao) {
        this.userDao = userDao;
        this.workoutDao = workoutDao;
        this.sportDao = sportDao;
        this.format = new Formatter();
    }
    
    /**
    * Method to check if the user is found and, if found, to set that user as logged in.
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
    * Method to log out any users by setting <code>userLoggedIn</code> as <code>null</code>.
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
    *
    * @throws java.lang.Exception if the new user cannot be saved.
    */
    public boolean newUser(String username, String name) throws Exception {
        
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
    * 
    * @throws java.lang.Exception if the new workout cannot be saved.
    */
    public boolean newWorkout(Workout workout) throws Exception {
        workoutDao.create(workout);
        return true;
    }
   
    /**
    * Method to delete a new workout. 
    *
    * @param    workout     <code>Workout</code> object for the new workout
    *
    * @return <code>true</code> if the workout was deleted; false otherwise.
    */
    public boolean deleteWorkout(Workout workout) {
        return workoutDao.remove(workout);
    }
    
    /**
    * Method to list all workouts for the named user.
    * 
    * @param    user     <code>User</code> object for which workouts should be listed
    * 
    * @return ArrayList of <code>Workout</code> objects for the user; empty list is <code>User</code> is null.
    * 
    * @throws java.lang.Exception if the workouts cannot be fetched.
    */
    public List<Workout> getWorkouts(User user) throws Exception {
        List<Workout> workouts = new ArrayList<>();
        if (user != null) {
            workouts = workoutDao.getAllByUser(user);
        }
        return workouts;
    }
    
    /**
    * Method to return a workout by its id number.
    * 
    * @param    id     id number of the <code>Workout</code> object
    * 
    * @return <code>Workout</code> object for the given id.
    * 
    * @throws java.lang.Exception if the workout cannot be fetched.
    */
    public Workout getWorkoutById(int id) throws Exception {
        return workoutDao.findById(id);
    }
    
    /**
    * Method to get total workout time for a named user as minutes.
    * 
    * @param    user     <code>User</code> object for which total workout time should be fetched
    * 
    * @return Total amount of workout minutes for the user.
    * 
    * @throws java.lang.Exception if the workouts cannot be fetched.
    */
    public int getTotalTime(User user) throws Exception {
        List<Workout> workouts = getWorkouts(user);
        int total = 0;
        total = workouts.stream().map((w) -> w.getDuration()).reduce(total, Integer::sum);
        return total;
    }
    
    /**
    * Method to get total workout time for a named user as hours and minutes.
    * 
    * @param    user     <code>User</code> object for which total workout time should be fetched
    * 
    * @return Total amount of workout time (in hours and minutes) for the user.
    * 
    * @throws java.lang.Exception if the workouts cannot be fetched.
    */
    public String getTotalTimeFormatted(User user) throws Exception {
        int total = getTotalTime(user);
        return format.minutesIntoHoursAndMinutes(total);
    }
    
    /**
    * Method to list all sport types.
    * 
    * @return ArrayList of <code>Sport</code> objects.
    */
    public List<Sport> getSports() {
        List<Sport> sports = sportDao.getAll();
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
