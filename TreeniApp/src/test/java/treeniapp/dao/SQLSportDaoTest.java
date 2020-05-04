
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
import treeniapp.domain.Sport;

public class SQLSportDaoTest {
    
    SportDao dao;
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
        sql.initialiseSportDatabase();
        dao = new SQLSportDao(sql);
    }
    
    @Test
    public void sportsCanBeReadFromDB() throws Exception {
        List<Sport> sports = dao.getAll();
        assertEquals(4, sports.size());
        Sport juoksu = sports.get(0);
        assertEquals("juoksu", juoksu.getName());
        assertEquals(true, juoksu.isShowDistance());
    }
    
    @Test
    public void sportIsFoundFromDB() {
        Sport koris = dao.findById(4);
        assertEquals("koripallo", koris.getName());
    }
  
    @Test
    public void newSportIsCreatedAndFoundFromDB() throws Exception {
        Sport newSport = new Sport(5, "testilaji", "test", false);
        dao.create(newSport);
        
        Sport sport = dao.findById(5);
        assertEquals("testilaji", sport.getName());
        assertEquals(false, sport.isShowDistance());
    }
    
    @After
    public void tearDown() {
        try {
            // Delete testing database
            sql.clearDatabase("Sports");
        } catch (Exception ex) {
            Logger.getLogger(SQLUserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
