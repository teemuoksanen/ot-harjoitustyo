
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.Sport;

/**
 * The definition of DAO interface that handles the reading and writing of <code>Sport</code> objects.
 */
public interface SportDao {

    /**
    * Saves the <code>Sport</code> object.
    * 
    * @param    sport   The <code>Sport</code> object to be saved.
    * @return   The <code>Sport</code> object.
    * @throws   java.lang.Exception if <code>Sport</code> object cannot be saved.
    */
    Sport create(Sport sport) throws Exception;
    
    /**
    * Finds a <code>Sport</code> object by its ID number.
    * 
    * @param    id   ID number of the <code>Sport</code> object.
    * @return   The <code>Sport</code> object.
    */
    Sport findById(int id);
    
    /**
    * Lists all <code>Sport</code> objects.
    * 
    * @return   A <code>List</code> containing all <code>Sport</code> objects.
    */
    List<Sport> getAll();
    
}
