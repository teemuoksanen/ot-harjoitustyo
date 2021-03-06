
package treeniapp.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import treeniapp.dao.SportDao;
import treeniapp.dao.UserDao;
import treeniapp.dao.WorkoutDao;
import treeniapp.domain.User;
import treeniapp.domain.Workout;

/**
 * Class contains methods to get and store <code>Workout</code> objects from and into SQL database.
 */
public class SQLWorkoutDao implements WorkoutDao {
    
    private SQLService sql;
    private UserDao userDao;
    private SportDao sportDao;
    
    /**
    * Constructor for <code>SQLWorkoutDao</code> with the <code>SQLService</code> injected.
    *
    * @param sql       <code>SQLService</code> class.
    * @param userDao   Class implementing <code>UserDao</code> interface.
    * @param sportDao  Class implementing <code>SportDao</code> class.
    */
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
    * @throws SQLException if the new workout cannot be stored.
    */
    @Override
    public Workout create(Workout workout) throws SQLException {
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
    * Method to find a workout by its id number.
    * 
    * @param    id   The id number of the <code>Workout</code> object to be fetched.
    * 
    * @return <code>Workout</code> object with the named id; <code>null</code> if not found.
    * @throws SQLException if the workout cannot be fetched.
    */
    @Override
    public Workout findById(int id) throws SQLException {
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
    * @throws SQLException if the workouts cannot be fetched.
    */
    @Override
    public List<Workout> getAll() throws SQLException {
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
    * @throws SQLException if the workouts cannot be fetched.
    */
    @Override
    public List<Workout> getAllByUser(User user) throws SQLException {
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

    /**
    * Method to remove a <code>Workout</code>.
    * 
    * @param workout   The <code>Workout</code> object that should be removed.
    * 
    * @return <code>true</code> if successfully removed; <code>false</code> otherwise.
    */
    @Override
    public boolean remove(Workout workout) {
        try {
            Connection connection = sql.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Workouts WHERE ID = ?");
            stmt.setInt(1, workout.getId());
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
