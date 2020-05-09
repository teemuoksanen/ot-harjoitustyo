
package treeniapp.domain.sql;

import java.sql.Timestamp;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import treeniapp.dao.SportDao;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.dao.sql.SQLService;
import treeniapp.dao.sql.SQLSportDao;
import treeniapp.dao.sql.SQLUserDao;
import treeniapp.dao.sql.SQLWorkoutDao;
import treeniapp.domain.TreeniAppService;
import treeniapp.domain.User;
import treeniapp.domain.Workout;

public class SQLServiceWorkoutTest {
    
    UserDao userDao;
    SportDao sportDao;
    WorkoutDao workoutDao;
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
    public void noWorkoutsIfNoUserLoggedIn() throws Exception {
        assertEquals(0, service.getWorkouts(null).size());
    }
    
    @Test
    public void newWorkoutCreatedSuccesfully() throws Exception {
        service.login("testaaja");
        Workout wo1 = new Workout(4, Timestamp.valueOf("2011-05-19 09:03:03.1234567"), userDao.findByUsername("testaaja"), sportDao.findById(1), 9, 1500, 125, "Testi");
        
        assertEquals(3, service.getWorkouts(userDao.findByUsername("testaaja")).size());
        assertTrue(service.newWorkout(wo1));
        assertEquals(4, service.getWorkouts(userDao.findByUsername("testaaja")).size());
        assertEquals(wo1.getDayMonthYear(), service.getWorkoutById(4).getDayMonthYear());
    }
    
    @Test
    public void workoutCreatedAndDeletedSuccesfully() throws Exception {
        service.login("testaaja");
        Workout wo1 = new Workout(4, Timestamp.valueOf("2019-05-19 09:03:03.1234567"), userDao.findByUsername("testaaja"), sportDao.findById(1), 9, 1500, 125, "Testi");
        
        assertEquals(4, service.getWorkouts(userDao.findByUsername("testaaja")).size());
        assertTrue(service.newWorkout(wo1));
        assertEquals(5, service.getWorkouts(userDao.findByUsername("testaaja")).size());
        assertTrue(service.deleteWorkout(service.getWorkoutById(4)));
        assertEquals(4, service.getWorkouts(userDao.findByUsername("testaaja")).size());
    }
    
    @Test
    public void totalWorkoutTimeIsEmptyForNewUser() throws Exception {
        User u = new User("uusi1", "Uusi Testikäyttäjä");
        
        assertEquals("0:00", service.getTotalTimeFormatted(u));
    }
    
    @Test
    public void totalWorkoutTimeIsCorrectAfterNewWorkoutsAreCreated() throws Exception {
        User u = new User("uusi2", "Uusi Testikäyttäjä");
        Workout wo1 = new Workout(4, Timestamp.valueOf("2019-05-19 09:03:03.1234567"), u, sportDao.findById(1), 61, 1500, 125, "Testi1");
        service.newWorkout(wo1);
        Workout wo2 = new Workout(5, Timestamp.valueOf("2019-06-19 09:03:03.1234567"), u, sportDao.findById(1), 5, 1500, 125, "Testi2");
        service.newWorkout(wo2);
        
        assertEquals("1:06", service.getTotalTimeFormatted(u));
    }
    
}
