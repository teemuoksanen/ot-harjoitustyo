
package treeniapp.dao;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import treeniapp.dao.sql.SQLService;
import treeniapp.dao.sql.SQLSportDao;
import treeniapp.dao.sql.SQLUserDao;
import treeniapp.dao.sql.SQLWorkoutDao;
import treeniapp.domain.User;
import treeniapp.domain.Workout;

public class SQLWorkoutDaoTest {
    WorkoutDao workoutDao;
    SportDao sportDao;
    UserDao userDao;
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
        
    }
    
    @Test
    public void newWorkoutIsCreated() {
        try {
            assertEquals(2, workoutDao.getAll().size());
            User user = userDao.findByUsername("testaaja");
            Workout wo1 = new Workout(3, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), user, sportDao.findById(1), 9, 1500, 125, "newWorkoutIsCreated");
            assertEquals("newWorkoutIsCreated", workoutDao.create(wo1).getNotes());
            assertEquals(3, workoutDao.getAll().size());
        } catch (Exception ex) {
            Logger.getLogger(SQLWorkoutDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void newWorkoutsAreCreatedForNewUser() {
        try {
            User user = new User("testi123", "Uusi Testaaja");
            userDao.create(user);
            Workout wo1 = new Workout(3, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), user, sportDao.findById(1), 9, 1500, 125, "Uusi Testaaja - eka treeni");
            Workout wo2 = new Workout(4, Timestamp.valueOf("2019-05-12 09:03:03.123456789"), user, sportDao.findById(3), 9, 1500, 125, "Uusi Testaaja - toka treeni");
            
            assertEquals(0, workoutDao.getAllByUser(user).size());
            assertEquals("Uusi Testaaja - eka treeni", workoutDao.create(wo1).getNotes());
            assertEquals(1, workoutDao.getAllByUser(user).size());
            workoutDao.create(wo2);
            assertEquals(2, workoutDao.getAllByUser(user).size());
        } catch (Exception ex) {
            Logger.getLogger(SQLWorkoutDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void newWorkoutsForAnotherUserNotCounted() {
        try {
            User user = new User("testi123", "Uusi Testaaja");
            User user2 = userDao.findByUsername("testaaja");
            userDao.create(user);
            Workout wo1 = new Workout(3, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), user, sportDao.findById(1), 9, 1500, 125, "Uusi Testaaja - eka treeni");
            Workout wo2 = new Workout(4, Timestamp.valueOf("2019-05-12 09:03:03.123456789"), user2, sportDao.findById(3), 9, 1500, 125, "Toisen käyttäjän treeni");
            
            assertEquals(0, workoutDao.getAllByUser(user).size());
            workoutDao.create(wo1);
            assertEquals(1, workoutDao.getAllByUser(user).size());
            workoutDao.create(wo2);
            assertEquals(1, workoutDao.getAllByUser(user).size());
        } catch (Exception ex) {
            Logger.getLogger(SQLWorkoutDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void correctWorkoutIsFoundById() {
        try {
            User user = userDao.findByUsername("testaaja");
            Workout wo1 = new Workout(3, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), user, sportDao.findById(1), 9, 1500, 99, "Uusi Testaaja - eka treeni");
            
            assertEquals(125, workoutDao.findById(1).getMhr());
            workoutDao.create(wo1);
            assertEquals(99, workoutDao.findById(3).getMhr());
        } catch (Exception ex) {
            Logger.getLogger(SQLWorkoutDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void workoutIsRemoved() {
        try {
            assertEquals(2, workoutDao.getAll().size());
            Workout wo = workoutDao.findById(1);
            workoutDao.remove(wo);
            assertEquals(1, workoutDao.getAll().size());
        } catch (Exception ex) {
            Logger.getLogger(SQLWorkoutDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
        try {
            // Delete testing database
            sql.clearDatabase("Users");
            sql.clearDatabase("Sports");
            sql.clearDatabase("Workouts");
        } catch (Exception ex) {
            Logger.getLogger(SQLWorkoutDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
