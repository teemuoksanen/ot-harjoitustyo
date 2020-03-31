
package treeniapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    FakeUserDao userDao;
    TreeniAppService service;
    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        service = new TreeniAppService(userDao);     
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
    
}
