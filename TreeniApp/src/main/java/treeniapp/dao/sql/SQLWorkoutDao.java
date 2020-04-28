
package treeniapp.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import treeniapp.dao.SportDao;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.domain.User;
import treeniapp.domain.Workout;

/**
 * Class contains methods to get and store <code>Workout</code> objects from and into SQL database
 */
public class SQLWorkoutDao implements WorkoutDao {
    
    private SQLService sql;
    private UserDao userDao;
    private SportDao sportDao;
    private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    public SQLWorkoutDao(SQLService sql, UserDao userDao, SportDao sportDao) {
        this.sql = sql;
        this.userDao = userDao;
        this.sportDao = sportDao;
    }

    /**
    * Method to store a new workout to the database.
    * 
    * @param    workout   The <code>Workout</code> object to be stored to the database.
    * 
    * @return <code>Workout</code> object that was stored to the database.
    */
    @Override
    public Workout create(Workout workout) throws Exception {
        Connection connection = sql.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Workouts"
                + "(datetime, user, sport, duration, distance, mhr, notes) VALUES (?, ?, ?, ?, ?, ?, ?)");
        stmt.setTimestamp(1, workout.getDatetime());
        stmt.setString(2, workout.getUser().getUsername());
        stmt.setInt(3, workout.getSport().getId());
        stmt.setInt(4, workout.getDuration());
        stmt.setInt(5, workout.getDistance());
        stmt.setInt(6, workout.getMhr());
        stmt.setString(7, workout.getNotes());
        stmt.executeUpdate();
        connection.close();
        return workout;
    }

    /**
    * Method to update a workout in the database.
    * 
    * @param    workout   The <code>Workout</code> object to be updated.
    * 
    * @return <code>Workout</code> object of the updated workout.
    */
    @Override
    public Workout update(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
    * Method to find a workout by its id number.
    * 
    * @param    id   The id number of the <code>Workout</code> object to be fetched.
    * 
    * @return <code>Workout</code> object with the named id; <code>null</code> if not found.
    */
    @Override
    public Workout findById(int id) throws Exception {
        Connection connection = sql.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Workouts WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) { 
            return null; 
        }

        Workout workout = new Workout(rs.getInt("id"), rs.getTimestamp("datetime"), 
                userDao.findByUsername(rs.getString("user")), sportDao.findById(rs.getInt("sport")), 
                rs.getInt("duration"), rs.getInt("distance"), rs.getInt("mhr"), rs.getString("notes"));

        connection.close();

        return workout;
    }

    /**
    * Method to list all workouts.
    * 
    * @return ArrayList containing all <code>Workout</code> objects; <code>null</code> if cannot be listed.
    */
    @Override
    public List<Workout> getAll() throws Exception {
        List<Workout> workouts = new ArrayList<>();
        
        Connection connection = sql.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Workouts");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Workout workout = new Workout(rs.getInt("id"), rs.getTimestamp("datetime"), 
                    userDao.findByUsername(rs.getString("user")), sportDao.findById(rs.getInt("sport")), 
                    rs.getInt("duration"), rs.getInt("distance"), rs.getInt("mhr"), rs.getString("notes"));
            workouts.add(workout);
        }

        connection.close(); 

        return workouts;
    }

    /**
    * Method to list all workouts of a <code>User</code>.
    * 
    * @param user   The <code>User</code> object of the user whose workouts should be listed.
    * 
    * @return ArrayList containing all <code>Workout</code> objects by a <code>User</code>; <code>null</code> if cannot be listed.
    */
    @Override
    public List<Workout> getAllByUser(User user) throws Exception {
        List<Workout> workouts = new ArrayList<>();
        
        Connection connection = sql.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Workouts WHERE user = ? ORDER BY datetime ASC");
        stmt.setString(1, user.getUsername());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Workout workout = new Workout(rs.getInt("id"), rs.getTimestamp("datetime"), user, 
                    sportDao.findById(rs.getInt("sport")), rs.getInt("duration"), rs.getInt("distance"), 
                    rs.getInt("mhr"), rs.getString("notes"));
            workouts.add(workout);
        }

        connection.close(); 

        return workouts;
    }
    
}
