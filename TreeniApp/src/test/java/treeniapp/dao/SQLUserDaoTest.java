
package treeniapp.dao;

import treeniapp.dao.sql.SQLUserDao;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import treeniapp.dao.sql.SQLService;
import treeniapp.domain.User;

public class SQLUserDaoTest {
    
    UserDao dao;
    SQLService sql;
    String databaseDB;
    String usernameDB;
    String passwordDB;
    
    @Before
    public void setUp() throws Exception {
        
        // Load SQL Service with test database
        databaseDB = "jdbc:h2:./treeniapptest";
        usernameDB = "sa";
        passwordDB = "";
        sql = new SQLService(databaseDB, usernameDB, passwordDB);
        sql.initialiseUserDatabase();
        dao = new SQLUserDao(sql);
        
    }
    
    @Test
    public void usersCanBeReadFromDB() throws Exception {
        List<User> users = dao.getAll();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals("Testikäyttäjä", user.getName());
        assertEquals("testaaja", user.getUsername());
    }
    
    @Test
    public void userIsFoundFromDB() {
        User user = dao.findByUsername("testaaja");
        assertEquals("Testikäyttäjä", user.getName());
        assertEquals("testaaja", user.getUsername());
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
        sql.clearDatabase("Users");
    }
    
}
