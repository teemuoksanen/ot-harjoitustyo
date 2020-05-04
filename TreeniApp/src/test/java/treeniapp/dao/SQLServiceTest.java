
package treeniapp.dao;

import java.util.List;
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


public class SQLServiceTest {
    
    WorkoutDao workoutDao;
    UserDao userDao;
    SportDao sportDao;
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
        sql.initialiseDatabases(true);
        userDao = new SQLUserDao(sql);
        sportDao = new SQLSportDao(sql);
        workoutDao = new SQLWorkoutDao(sql, userDao, sportDao);
    }
    
    @Test
    public void initialDataCanBeRead() throws Exception {
        List<Workout> workouts = workoutDao.getAll();
        assertEquals(2, workouts.size());
        User user = workouts.get(0).getUser();
        assertEquals("Testikäyttäjä", user.getName());
        assertEquals("testaaja", user.getUsername());
    }
    
    @After
    public void tearDown() {
        try {
            // Delete testing databases
            sql.clearDatabase("Users");
            sql.clearDatabase("Workouts");
            sql.clearDatabase("Sports");
        } catch (Exception ex) {
            Logger.getLogger(SQLUserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
