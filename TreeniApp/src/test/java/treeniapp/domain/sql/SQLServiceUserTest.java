
package treeniapp.domain.sql;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.dao.SportDao;
import treeniapp.dao.sql.SQLService;
import treeniapp.dao.sql.SQLSportDao;
import treeniapp.dao.sql.SQLUserDao;
import treeniapp.dao.sql.SQLWorkoutDao;
import treeniapp.domain.TreeniAppService;
import treeniapp.domain.User;

public class SQLServiceUserTest {
    
    UserDao userDao;
    WorkoutDao workoutDao;
    SportDao sportDao;
    TreeniAppService service;
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
        sql.initialiseSportDatabase();
        sql.initialiseWorkoutDatabase();
        userDao = new SQLUserDao(sql);
        sportDao = new SQLSportDao(sql);
        workoutDao = new SQLWorkoutDao(sql, userDao, sportDao);
        service = new TreeniAppService(userDao, workoutDao, sportDao);     
    }
    
    @Test
    public void loginSuccessfull() {
        boolean result = service.login("testaaja");
        User loggedInUser = service.getLoggedInUser();
        
        assertTrue(result);
        assertEquals("testaaja", loggedInUser.getUsername());
        assertEquals("Testikäyttäjä", loggedInUser.getName());
    }
    
    @Test
    public void logoutSuccessfull() {
        service.login("testaaja");
        service.logout();
        
        assertEquals(null, service.getLoggedInUser());
    }
    
    @Test
    public void noLoginWithWrongUsername() {
        boolean result = service.login("wronguser");
        
        assertFalse(result);
        assertEquals(null, service.getLoggedInUser());
    }
    
    @Test
    public void newUserCanBeCreatedAndCanThenLogin() throws Exception {
        boolean result = service.newUser("newuser", "New User");
        boolean result2 = service.login("newuser");
        User loggedInUser = service.getLoggedInUser();
        
        assertTrue(result);
        assertTrue(result2);
        assertEquals("newuser", loggedInUser.getUsername());
        assertEquals("New User", loggedInUser.getName());
    }
    
    @Test
    public void newUserCantBeCreatedIfSameLogin() throws Exception {
        boolean result = service.newUser("newuser", "New User");
        boolean result2 = service.newUser("newuser", "New User 2");
        
        assertFalse(result2);
    }
    
    @Test
    public void usersNameCanBeChanged() throws Exception {
        service.newUser("newuser", "New User");
        service.login("newuser");
        User changedUser = new User("newuser", "New User Changed");
        
        boolean result = service.updateUser(changedUser);
        assertTrue(result);
        assertEquals(service.getLoggedInUser().getName(), changedUser.getName());
    }
    
}
