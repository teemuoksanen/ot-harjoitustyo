
package treeniapp.domain.fake;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import treeniapp.dao.FakeUserDao;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.dao.SportDao;
import treeniapp.domain.TreeniAppService;
import treeniapp.domain.User;

public class ServiceUserTest {
    
    UserDao userDao;
    WorkoutDao workoutDao;
    SportDao sportDao;
    TreeniAppService service;
    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        workoutDao = null;
        sportDao = null;
        service = new TreeniAppService(userDao, workoutDao, sportDao);     
    }
    
    @Test
    public void loginSuccessfull() {
        boolean result = service.login("testi");
        User loggedInUser = service.getLoggedInUser();
        
        assertTrue(result);
        assertEquals("testi", loggedInUser.getUsername());
        assertEquals("Teemu Testi", loggedInUser.getName());
    }
    
    @Test
    public void logoutSuccessfull() {
        service.login("testi");
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
