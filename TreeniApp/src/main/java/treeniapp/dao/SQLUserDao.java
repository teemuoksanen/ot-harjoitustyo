
package treeniapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import treeniapp.domain.User;

public class SQLUserDao implements UserDao {
    
    private List<User> users;
    private Map<String, User> userset;
    private String databaseDB;
    private String usernameDB;
    private String passwordDB;
    private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    public SQLUserDao(String databaseDB, String usernameDB, String passwordDB) throws Exception {
        this.databaseDB = databaseDB;
        this.usernameDB = usernameDB;
        this.passwordDB = passwordDB;
        this.users = new ArrayList<>();
        this.userset = new HashMap<>();
        
        Connection connection = DriverManager.getConnection(databaseDB, usernameDB, passwordDB);

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String username = rs.getString("username");
            User user = new User(username, rs.getString("name"));
            users.add(user);
            userset.put(username, user);
        }

        stmt.close();
        rs.close();
        connection.close();        
    }
    
    @Override
    public List<User> getAll() {
        return users;
    }
    
    @Override
    public User findByUsername(String username) {
        User user = userset.getOrDefault(username, null);
        return user;
    }
    
    @Override
    public User create(User user) {
        try {
            Connection connection = DriverManager.getConnection(this.databaseDB, this.usernameDB, this.passwordDB);

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users"
                + " (username, name)"
                + " VALUES (?, ?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getName());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            
            users.add(user);
            userset.put(user.getUsername(), user);

            return user;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
