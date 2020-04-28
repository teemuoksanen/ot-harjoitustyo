
package treeniapp.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import treeniapp.dao.UserDao;
import treeniapp.domain.User;

/**
 * Class contains methods to get and store <code>User</code> objects from and into SQL database
 */
public class SQLUserDao implements UserDao {
    
    private SQLService sql;
    private List<User> users;
    private Map<String, User> userMap;
    private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    public SQLUserDao(SQLService sql) throws Exception {
        this.sql = sql;
        this.users = new ArrayList<>();
        this.userMap = new HashMap<>();
        
        getInitialUsers();
    }
    
    /**
    * Method to initially fetch all users from the database and stored to an ArrayList and a HashMap.
    */
    private void getInitialUsers() throws Exception {
        Connection connection = sql.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String username = rs.getString("username");
            User user = new User(username, rs.getString("name"));
            users.add(user);
            userMap.put(username, user);
        }

        stmt.close();
        rs.close();
        connection.close();
    }
    
    /**
    * Method to list all users.
    * 
    * @return ArrayList containing all <code>User</code> objects.
    */
    @Override
    public List<User> getAll() {
        return users;
    }
    
    /**
    * Method to find a user by its username.
    * 
    * @param    username   The username of the <code>User</code> object to be fetched.
    * 
    * @return <code>User</code> object with the named username; <code>null</code> if not found.
    */
    @Override
    public User findByUsername(String username) {
        User user = userMap.getOrDefault(username, null);
        return user;
    }
    
    /**
    * Method to store a new user to the database.
    * 
    * @param    user   The <code>User</code> object to be stored to the database.
    * 
    * @return <code>User</code> object that was stored to the database.
    */
    @Override
    public User create(User user) throws Exception {
        Connection connection = sql.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users"
            + " (username, name)"
            + " VALUES (?, ?)");
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getName());
        stmt.executeUpdate();
        stmt.close();
        connection.close();

        users.add(user);
        userMap.put(user.getUsername(), user);

        return user;
    }
    
}
