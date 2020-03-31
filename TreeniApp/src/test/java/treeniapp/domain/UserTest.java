
package treeniapp.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class UserTest {
    
    FakeUserDao userDao;
    TreeniAppService service;
    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        service = new TreeniAppService(userDao);     
    }
    
    @Test
    public void noLoginWithWrongUsername() {
        boolean result = service.login("wronguser");
        
        assertFalse(result);
        assertEquals(null, service.getLoggedInUser());
    }
    
}
