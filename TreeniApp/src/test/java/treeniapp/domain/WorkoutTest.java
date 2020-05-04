
package treeniapp.domain;

import java.sql.Timestamp;
import org.junit.Test;
import static org.junit.Assert.*;
import treeniapp.dao.FakeSportDao;
import treeniapp.dao.FakeUserDao;
import treeniapp.dao.SportDao;
import treeniapp.dao.UserDao;

public class WorkoutTest {
    
    UserDao userDao = new FakeUserDao();
    SportDao sportDao = new FakeSportDao();
    
    @Test
    public void workoutDurationCorrectIfUnder10Minutes() {
        Workout wo1 = new Workout(1, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 3, 1500, 125, "Testi");
        
        assertEquals("0:03", wo1.getDurationFormatted());
    }
    
    @Test
    public void workoutDurationCorrectIf1Hour() {
        Workout wo1 = new Workout(1, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 60, 1500, 125, "Testi");
        
        assertEquals("1:00", wo1.getDurationFormatted());
    }
    
    @Test
    public void workoutDurationCorrectIfOver10Hours() {
        Workout wo1 = new Workout(1, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 610, 1500, 125, "Testi");
        
        assertEquals("10:10", wo1.getDurationFormatted());
    }
    
    @Test
    public void workoutDistanceCorrectIfUnder1Km() {
        Workout wo1 = new Workout(1, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 3, 100, 125, "Testi");
        
        assertEquals("100 m", wo1.getDistanceFormatted());
    }
    
    @Test
    public void workoutDistanceCorrectIfExactly1Km() {
        Workout wo1 = new Workout(1, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 3, 1000, 125, "Testi");
        
        assertEquals("1 km", wo1.getDistanceFormatted());
    }
    
    @Test
    public void workoutDurationCorrectIfRandom() {
        Workout wo1 = new Workout(1, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 610, 23456, 125, "Testi");
        
        assertEquals("23 km  456 m", wo1.getDistanceFormatted());
    }
    
    @Test
    public void workoutDateFormattingsWork() {
        Workout wo1 = new Workout(1, Timestamp.valueOf("2019-05-19 09:03:03.123456789"), userDao.findByUsername("testi"), sportDao.findById(1), 9, 1500, 125, "Testi");
        
        assertEquals("19", wo1.getDay());
        assertEquals("5", wo1.getMonth());
        assertEquals("19.5.", wo1.getDayMonth());
        assertEquals("19.5.2019", wo1.getDayMonthYear());
        assertEquals("9", wo1.getHour());
        assertEquals("03", wo1.getMinute());
        assertEquals("9.03", wo1.getTime());
        assertEquals("2019", wo1.getYear());
    }
    
}
