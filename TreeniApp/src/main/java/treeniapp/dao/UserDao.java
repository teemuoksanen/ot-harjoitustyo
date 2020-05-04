
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.User;

/**
 * The definition of DAO interface that handles the reading and writing of <code>User</code> objects.
 */
public interface UserDao {
    
    /**
    * Lists all <code>User</code> objects.
    * @return  A <code>List</code> containing all <code>User</code> objects.
    */
    List<User> getAll();
    
    /**
    * Finds a <code>User</code> object by its username.
    * @param    username   Username of the <code>User</code> object.
    * @return   The <code>User</code> object.
    */
    User findByUsername(String username);
    
    /**
    * Saves the <code>User</code> object.
    * @param    user   The <code>User</code> object to be saved.
    */
    User create(User user) throws Exception;
    
}