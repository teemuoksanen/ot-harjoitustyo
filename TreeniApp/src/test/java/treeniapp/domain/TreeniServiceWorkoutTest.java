
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
    public void noWorkoutsIfNoUserLoggedIn() throws Exception {
        assertEquals(0, service.getWorkouts(null).size());
    }
    
    @Test
    public void newWorkoutCreatedSuccesfully() throws Exception {
        service.login("testi");
        Workout wo1 = new Workout(3, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 9, 1500, 125, "Testi");
        
        assertEquals(1, service.getWorkouts(userDao.findByUsername("testi")).size());
        assertTrue(service.newWorkout(wo1));
        assertEquals(2, service.getWorkouts(userDao.findByUsername("testi")).size());
        assertEquals(wo1, service.getWorkoutById(3));
    }
    
    @Test
    public void totalWorkoutTimeIsEmptyForNewUser() throws Exception {
        User u = new User("uusi", "Uusi Testikäyttäjä");
        
        assertEquals("0:00", service.getTotalTimeFormatted(u));
    }
    
    @Test
    public void totalWorkoutTimeIsCorrectAfterNewWorkoutsAreCreated() throws Exception {
        User u = new User("uusi", "Uusi Testikäyttäjä");
        Workout wo1 = new Workout(3, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), u, sportDao.findById(1), 61, 1500, 125, "Testi1");
        service.newWorkout(wo1);
        Workout wo2 = new Workout(4, Timestamp.valueOf("2019-06-19 09:03:03.123456789"), u, sportDao.findById(1), 5, 1500, 125, "Testi2");
        service.newWorkout(wo2);
        
        assertEquals("1:06", service.getTotalTimeFormatted(u));
    }
    
    
}
