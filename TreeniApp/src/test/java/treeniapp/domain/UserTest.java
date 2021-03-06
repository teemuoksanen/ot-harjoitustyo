
package treeniapp.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    @Test
    public void sameUsernamesAreEqual() {
        User user1 = new User("user", "User 1");
        User user2 = new User("user", "User 2");
        
        assertTrue(user1.equals(user2));
    }
    
    @Test
    public void differentUsernamesAreNotEqual() {
        User user1 = new User("user", "User 1");
        User user2 = new User("user2", "User 2");
        
        assertFalse(user1.equals(user2));
    }
    
    @Test
    public void differentObjectsAreNotEqual() {
        User user1 = new User("user", "User 1");
        Object object = new Object();
        
        assertFalse(user1.equals(object));
    }
    
}
