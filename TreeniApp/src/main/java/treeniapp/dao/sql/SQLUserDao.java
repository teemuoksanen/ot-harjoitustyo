
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

public class SQLUserDao implements UserDao {
    
    private SQLService sql;
    private List<User> users;
    private Map<String, User> userMap;
    private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    public SQLUserDao(SQLService sql) {
        this.sql = sql;
        this.users = new ArrayList<>();
        this.userMap = new HashMap<>();
        
        getInitialUsers();
    }
    
    private void getInitialUsers() {
        try {
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
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<User> getAll() {
        return users;
    }
    
    @Override
    public User findByUsername(String username) {
        User user = userMap.getOrDefault(username, null);
        return user;
    }
    
    @Override
    public User create(User user) {
        try {
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
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
