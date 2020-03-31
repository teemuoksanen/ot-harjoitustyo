
package treeniapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import treeniapp.domain.User;

public class SQLUserDao implements UserDao {
    
    private List<User> users;
    
    public SQLUserDao() throws Exception {
        this.users = new ArrayList<>();
        
        Connection connection = DriverManager.getConnection("jdbc:h2:./treeniapp", "sa", "");

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users");
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            User user = new User(rs.getString("username"), rs.getString("name"));
            users.add(user);
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
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:./treeniapp", "sa", "");
            
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if(!rs.next()) {
                return null;
            }
            
            User user = new User(rs.getString("username"), rs.getString("name"));
            
            stmt.close();
            rs.close();
            connection.close();
            
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(SQLUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public User create(User user) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./treeniapp", "sa", "");

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users"
            + " (username, name)"
            + " VALUES (?, ?)");
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getName());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
        
        return user;
    }
    
}
