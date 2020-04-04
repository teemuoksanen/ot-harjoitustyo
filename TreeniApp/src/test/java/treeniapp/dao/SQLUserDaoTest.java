
package treeniapp.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import treeniapp.Main;
import treeniapp.domain.User;

public class SQLUserDaoTest {
    
    UserDao dao;
    String databaseDB;
    String usernameDB;
    String passwordDB;
    private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    @Before
    public void setUp() throws Exception {
        
        // Load properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        databaseDB = "jdbc:h2:./treeniapptest";
        usernameDB = properties.getProperty("usernameDB");
        passwordDB = properties.getProperty("passwordDB");
        
        // Create testing database
        try (Connection connection = DriverManager.getConnection(databaseDB, usernameDB, passwordDB);) {
            connection.prepareStatement("DROP TABLE Users IF EXISTS;").executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users (\n"
                    + "    username VARCHAR(15) PRIMARY KEY,\n"
                    + "    name VARCHAR(20)\n"
                    + ");").executeUpdate();
            connection.prepareStatement("INSERT INTO Users (username, name) VALUES ('tester1', 'Test User 1'), ('tester2', 'Test User 2');").executeUpdate();
            connection.close();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        dao = new SQLUserDao(databaseDB, usernameDB, passwordDB);
        
    }
    
    @Test
    public void usersCanBeReadFromDB() throws Exception {
        List<User> users = dao.getAll();
        assertEquals(2, users.size());
        User user = users.get(1);
        assertEquals("Test User 2", user.getName());
        assertEquals("tester2", user.getUsername());
    }
    
    @Test
    public void userIsFoundFromDB() {
        User user = dao.findByUsername("tester2");
        assertEquals("Test User 2", user.getName());
        assertEquals("tester2", user.getUsername());
    }
    
    @Test
    public void nonExistingUserIsNotFoundFromDB() {
        User user = dao.findByUsername("nonuser");
        assertEquals(null, user);
    }
  
    @Test
    public void newUserIsCreatedAndFoundFromDB() throws Exception {
        User newUser = new User("newtest", "New Test User");
        dao.create(newUser);
        
        User user = dao.findByUsername("newtest");
        assertEquals("New Test User", user.getName());
        assertEquals("newtest", user.getUsername());
    }
    
    @After
    public void tearDown() {
        // Delete testing database
        try (Connection connection = DriverManager.getConnection(databaseDB, usernameDB, passwordDB);) {
            connection.prepareStatement("DROP TABLE Users IF EXISTS;").executeUpdate();
            connection.close();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
}
