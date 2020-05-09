
package treeniapp.domain.fake;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import treeniapp.dao.FakeSportDao;
import treeniapp.dao.FakeUserDao;
import treeniapp.dao.FakeWorkoutDao;
import treeniapp.dao.SportDao;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.domain.Sport;
import treeniapp.domain.TreeniAppService;

public class ServiceSportTest {
    
    UserDao userDao;
    WorkoutDao workoutDao;
    SportDao sportDao;
    TreeniAppService service;
    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        workoutDao = new FakeWorkoutDao();
        sportDao = new FakeSportDao();
        service = new TreeniAppService(userDao, workoutDao, sportDao);     
    }
    
    @Test
    public void getSportsReturnsAll() {
        assertEquals(4, service.getSports().size());
    }
    
    @Test
    public void getSportByIdReturnsOneCorrectSport() {
        assertEquals("koripallo", service.getSportById(4).getName());
        assertEquals("basketball", service.getSportById(4).getIcon());
    }
    
    @Test
    public void newSportCanBeCreatedAndFound() {
        boolean result = service.newSport("testilaji", "testi", true);
        Sport newSport = service.getSportByName("testilaji");
        
        assertTrue(result);
        assertEquals("testi", newSport.getIcon());
    }
    
    @Test
    public void noNewSportWithSameNameCanBeCreated() {
        boolean result = service.newSport("juoksu", "running", true);
        
        assertFalse(result);
    }
    
}
