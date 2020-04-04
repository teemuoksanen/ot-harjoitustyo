
package treeniapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import treeniapp.domain.User;
import treeniapp.domain.Workout;

public class SQLWorkoutDao implements WorkoutDao {
    
    private String databaseDB;
    private String usernameDB;
    private String passwordDB;
    private UserDao userDao;
    private SportDao sportDao;
    private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    public SQLWorkoutDao(String databaseDB, String usernameDB, String passwordDB) throws Exception {
        this.databaseDB = databaseDB;
        this.usernameDB = usernameDB;
        this.passwordDB = passwordDB;
        this.userDao = new SQLUserDao(databaseDB, usernameDB, passwordDB);
        this.sportDao = new TempSportDao(databaseDB, usernameDB, passwordDB);
    }

    @Override
    public Workout create(Workout workout) {
        try {
            Connection connection = DriverManager.getConnection(this.databaseDB, this.usernameDB, this.passwordDB);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Workouts"
                + " (datetime, user, sport, duration, distance, mhr, notes)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setTimestamp(1, workout.getDatetime());
            stmt.setString(2, workout.getUser().getUsername());
            stmt.setInt(3, workout.getSport().getId());
            stmt.setInt(4, workout.getDuration());
            stmt.setInt(5, workout.getDistance());
            stmt.setInt(6, workout.getMhr());
            stmt.setString(7, workout.getNotes());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            return workout;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Workout update(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Workout findById(int id) {
        try {
            Connection connection = DriverManager.getConnection(this.databaseDB, this.usernameDB, this.passwordDB);
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Workouts WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                return null;
            }
            
            Workout workout = new Workout(rs.getInt("id"), 
                    rs.getTimestamp("datetime"), 
                    userDao.findByUsername(rs.getString("user")), 
                    sportDao.findById(rs.getInt("sport")), 
                    rs.getInt("duration"), 
                    rs.getInt("distance"), 
                    rs.getInt("mhr"), 
                    rs.getString("notes"));
            
            stmt.close();
            rs.close();
            connection.close();
            
            return workout;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Workout> getAll() {
        List<Workout> workouts = new ArrayList<>();
        
        try {
            Connection connection = DriverManager.getConnection(databaseDB, usernameDB, passwordDB);

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Workouts");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Workout workout = new Workout(rs.getInt("id"), 
                        rs.getTimestamp("datetime"), 
                        userDao.findByUsername(rs.getString("user")), 
                        sportDao.findById(rs.getInt("sport")), 
                        rs.getInt("duration"), 
                        rs.getInt("distance"), 
                        rs.getInt("mhr"), 
                        rs.getString("notes"));
                workouts.add(workout);
            }

            stmt.close();
            rs.close();
            connection.close(); 

            return workouts;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public List<Workout> getAllByUser(User user) {
        List<Workout> workouts = new ArrayList<>();
        System.out.println("Haetaan treenit tietokannasta käyttäjälle " + user.getUsername() + "...");
        
        try {
            Connection connection = DriverManager.getConnection(databaseDB, usernameDB, passwordDB);
            

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Workouts WHERE user = ?");
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Workout workout = new Workout(rs.getInt("id"), 
                        rs.getTimestamp("datetime"), 
                        user, 
                        sportDao.findById(rs.getInt("sport")), 
                        rs.getInt("duration"), 
                        rs.getInt("distance"), 
                        rs.getInt("mhr"), 
                        rs.getString("notes"));
                workouts.add(workout);
            }

            stmt.close();
            rs.close();
            connection.close(); 

            return workouts;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
