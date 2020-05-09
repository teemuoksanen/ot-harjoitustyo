
package treeniapp.domain.sql;

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
import treeniapp.domain.Sport;
import treeniapp.domain.TreeniAppService;

public class SQLServiceSportTest {
    
    UserDao userDao;
    WorkoutDao workoutDao;
    SportDao sportDao;
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
    public void getSportsReturnsAll() {
        assertEquals(9, service.getSports().size());
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
