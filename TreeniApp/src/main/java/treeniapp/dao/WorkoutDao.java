
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.Workout;
import treeniapp.domain.User;

/**
 * The definition of DAO interface that handles the reading and writing of <code>Workout</code> objects.
 */
public interface WorkoutDao {

    /**
    * Saves the <code>Workout</code> object.
    * 
    * @param    workout   The <code>Workout</code> object to be saved.
    * @return   The <code>Workout</code> object.
    * @throws   java.lang.Exception if <code>Workout</code> object cannot be saved.
    */
    Workout create(Workout workout) throws Exception;
    
    /**
    * Finds a <code>Workout</code> object by its ID number.
    * 
    * @param    id   ID number of the <code>Workout</code> object.
    * @return   The <code>Workout</code> object.
    * @throws   java.lang.Exception if fetching <code>Workout</code> object from storage leads to an error.
    */
    Workout findById(int id) throws Exception;
    
    /**
    * Lists all <code>Workout</code> objects.
    * 
    * @return   A <code>List</code> containing all <code>Workout</code> objects.
    * @throws   java.lang.Exception if fetching <code>Workout</code> objects from storage leads to an error.
    */
    List<Workout> getAll() throws Exception;
    
    /**
    * Lists all <code>Workout</code> objects for a defined <code>User</code>.
    * 
    * @param    user     The <code>User</code> object whose <code>Workout</code>s should be listed.
    * @return   A <code>List</code> containing <code>Workout</code> objects.
    * @throws   java.lang.Exception if fetching <code>Workout</code> objects from storage leads to an error.
    */
    List<Workout> getAllByUser(User user) throws Exception;
    
    /**
    * Removes the <code>Workout</code> object from storage.
    * 
    * @param    workout   The <code>Workout</code> object to be removed.
    * @return   <code>true</code> if removal succeeds; <code>false</code> otherwise.
    */
    boolean remove(Workout workout);

}