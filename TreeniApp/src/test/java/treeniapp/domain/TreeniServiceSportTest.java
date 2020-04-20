
package treeniapp.domain;

import java.sql.Timestamp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import treeniapp.dao.FakeSportDao;
import treeniapp.dao.FakeUserDao;
import treeniapp.dao.FakeWorkoutDao;
import treeniapp.dao.SportDao;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;

public class TreeniServiceSportTest {
    
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
    
}
