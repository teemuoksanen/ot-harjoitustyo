
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

public class TreeniServiceWorkoutTest {
    
    UserDao userDao;
    SportDao sportDao;
    WorkoutDao workoutDao;
    TreeniAppService service;
    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        workoutDao = new FakeWorkoutDao();
        sportDao = new FakeSportDao();
        service = new TreeniAppService(userDao, workoutDao, sportDao);     
    }
    
    @Test
    public void newWorkoutCreatedSuccesfully() {
        service.login("testi");
        Workout wo1 = new Workout(3, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 9, 1500, 125, "Testi");
        
        assertEquals(1, service.getWorkouts().size());
        assertTrue(service.newWorkout(wo1));
        assertEquals(2, service.getWorkouts().size());
    }
    
    
}
