
package treeniapp.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class SportTest {
    
    @Test
    public void toStringReturnsCorrectName() {
        Sport sport = new Sport(99, "testilaji", "testi", false);
        
        assertEquals("testilaji", sport.toString());
    }
    
}
