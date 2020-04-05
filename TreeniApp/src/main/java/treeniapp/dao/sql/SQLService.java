
package treeniapp.dao.sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLService {
    
    private String database;
    private String username;
    private String password;
    
    public SQLService() throws FileNotFoundException, IOException, InvalidPropertiesFormatException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        database = properties.getProperty("databaseDB");
        username = properties.getProperty("usernameDB");
        password = properties.getProperty("passwordDB");
    }
    
    // Replace normal values for testing purposes
    public SQLService(String databaseDB, String usernameDB, String passwordDB) {
        database = databaseDB;
        username = usernameDB;
        password = passwordDB;
    }
    
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.database, this.username, this.password);
        } catch (SQLException ex) {
            handleSQLException(ex);
        }
        return connection;
    }
    
    public static void closeConnection(Connection connection) {        
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            handleSQLException(ex);
        }
    }
    
    public void initialiseDatabases(Boolean clear) {
        if (clear) {
            clearDatabase("Users");
            clearDatabase("Workouts");
            clearDatabase("Sports");
        }
        initialiseUserDatabase();
        initialiseWorkoutDatabase();
        initialiseSportDatabase();
    }
    
    public void initialiseUserDatabase() {
        Connection connection = getConnection();
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users ("
                    + "    username VARCHAR(15) PRIMARY KEY,    name VARCHAR(20) NOT NULL"
                    + ");").executeUpdate();
            connection.prepareStatement("MERGE INTO Users (username, name) VALUES "
                    + "('testaaja', 'Testikäyttäjä');").executeUpdate();
        } catch (SQLException ex) {
            handleSQLException(ex);
        } 
        closeConnection(connection);
    }
    
    public void initialiseWorkoutDatabase() {
        Connection connection = getConnection();
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Workouts ("
                    + "    id INT AUTO_INCREMENT PRIMARY KEY,   user VARCHAR(15) NOT NULL,"
                    + "    datetime TIMESTAMP NOT NULL,         sport INT NOT NULL,"
                    + "    duration INT,                        distance INT,"
                    + "    mhr INT,                             notes VARCHAR(250)"
                    + ");").executeUpdate();
            connection.prepareStatement("MERGE INTO Workouts (id, datetime, user, sport, duration, distance, mhr, notes) VALUES\n"
                    + "    (1, '2020-02-15 12:01:01', 'testaaja', 1, 65, 1500, 125, 'Juoksua parvekkeella'),\n"
                    + "    (2, '2020-02-19 14:02:02', 'testaaja', 2, 35, 0, 110, 'Ulkokuntosali')\n"
                    + ";").executeUpdate();
        } catch (SQLException ex) {
            handleSQLException(ex);
        } 
        closeConnection(connection);
    }
    
    public void initialiseSportDatabase() {
        Connection connection = getConnection();
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Sports ("
                    + "    id INT AUTO_INCREMENT PRIMARY KEY,   name VARCHAR(15) NOT NULL,"
                    + "    icon VARCHAR(20),                    showdist BOOLEAN"
                    + ");").executeUpdate();
            connection.prepareStatement("MERGE INTO Sports (id, name, icon, showdist) VALUES"
                    + "    (1, 'juoksu', 'running', 'TRUE'),"
                    + "    (2, 'kuntosali', 'gym', 'FALSE'),"
                    + "    (3, 'uinti', 'swimming', 'TRUE'),"
                    + "    (4, 'koripallo', 'basketball', 'FALSE')"
                    + ";").executeUpdate();
        } catch (SQLException ex) {
            handleSQLException(ex);
        } 
        closeConnection(connection);
    }
    
    public void clearDatabase(String table) {
        Connection connection = getConnection();
        try {
            connection.prepareStatement("DROP TABLE " + table + " IF EXISTS;").executeUpdate();
        } catch (SQLException ex) {
            handleSQLException(ex);
        } 
        closeConnection(connection);
    }

    public static void handleSQLException(SQLException ex) {
        Logger.getLogger(SQLService.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("SQL ERROR");
        System.err.println(ex);
    }
    
}
