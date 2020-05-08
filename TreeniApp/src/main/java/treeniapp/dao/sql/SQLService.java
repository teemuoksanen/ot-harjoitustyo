
package treeniapp.dao.sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Class offers SQL related services for other classes.
 */
public class SQLService {
    
    private String database;
    private String username;
    private String password;
    
    /**
    * Constructor that fetches the database's details from 'config.properties' file.
    * 
    * @throws FileNotFoundException if file 'config.properties' cannot be found.
    * @throws IOException if file 'config.properties' could not be read.
    * @throws InvalidPropertiesFormatException if file 'config.properties' is invalid.
    */
    public SQLService() throws FileNotFoundException, IOException, InvalidPropertiesFormatException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        database = properties.getProperty("databaseDB");
        username = properties.getProperty("usernameDB");
        password = properties.getProperty("passwordDB");
    }
    
    /**
    * Constructor where the database's details are given as parameters.
    * 
    * @param databaseDB    Name of the database.
    * @param usernameDB    Username to access the database.
    * @param passwordDB    Password to access the database.
    */
    public SQLService(String databaseDB, String usernameDB, String passwordDB) {
        database = databaseDB;
        username = usernameDB;
        password = passwordDB;
    }
    
    /**
    * Method to open SQL connection.
    *
    * @return <code>Connection</code> object.
    * 
    * @throws SQLException if the SQL connection cannot be opened.
    */
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(this.database, this.username, this.password);
        return connection;
    }
    
    /**
    * Method to (clear and) initialise all application's basic database tables.
    *
    * @param    clear   <code>true</code> to first clear all database tables; <code>false</code> to just run initialisation.
    * @throws   SQLException if there is an error when initialising the database tables.
    */
    public void initialiseDatabases(Boolean clear) throws SQLException {
        if (clear) {
            clearDatabase("Users");
            clearDatabase("Workouts");
            clearDatabase("Sports");
        }
        initialiseUserDatabase();
        initialiseWorkoutDatabase();
        initialiseSportDatabase();
    }
    
    /**
    * Method to initialise the user database table.
    * 
    * @throws   SQLException if there is an error when initialising the user database table.
    */
    public void initialiseUserDatabase() throws SQLException {
        Connection connection = getConnection();
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users ("
                + "    username VARCHAR(15) PRIMARY KEY,    name VARCHAR(20) NOT NULL"
                + ");").executeUpdate();
        connection.prepareStatement("MERGE INTO Users (username, name) VALUES "
                + "('testaaja', 'Testikäyttäjä');").executeUpdate();
        connection.close();
    }
    
    /**
    * Method to initialise the workout database table.
    * 
    * @throws   SQLException if there is an error when initialising the workout database table.
    */
    public void initialiseWorkoutDatabase() throws SQLException {
        Connection connection = getConnection();
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS Workouts ("
                + "    id INT AUTO_INCREMENT PRIMARY KEY,   user VARCHAR(15) NOT NULL,"
                + "    datetime TIMESTAMP NOT NULL,         sport INT NOT NULL,"
                + "    duration INT,                        distance INT,"
                + "    mhr INT,                             notes VARCHAR(250)"
                + ");").executeUpdate();
        connection.prepareStatement("MERGE INTO Workouts (id, datetime, user, sport, duration, distance, mhr, notes) VALUES\n"
                + "    (1, '2020-02-15 12:01:01', 'testaaja', 1, 65, 1500, 125, 'Juoksua parvekkeella'),\n"
                + "    (2, '2020-02-19 14:02:02', 'testaaja', 2, 35, 0, 110, 'Ulkokuntosali'),\n"
                + "    (3, '2019-04-01 11:22:33', 'testaaja', 3, 49, 2500, 130, '')\n"
                + ";").executeUpdate();
        connection.close();
    }
    
    /**
    * Method to initialise the sport database table.
    * 
    * @throws   SQLException if there is an error when initialising the sport database table.
    */
    public void initialiseSportDatabase() throws SQLException {
        Connection connection = getConnection();
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS Sports ("
                + "    id INT AUTO_INCREMENT PRIMARY KEY,   name VARCHAR(15) NOT NULL,"
                + "    icon VARCHAR(20),                    showdist BOOLEAN"
                + ");").executeUpdate();
        connection.prepareStatement("MERGE INTO Sports (id, name, icon, showdist) VALUES"
                + "    (1, 'juoksu', 'running', 'TRUE'),"
                + "    (2, 'kuntosali', 'gym', 'FALSE'),"
                + "    (3, 'uinti', 'swimming', 'TRUE'),"
                + "    (4, 'koripallo', 'basketball', 'FALSE'),"
                + "    (5, 'ryhmäliikunta', 'group', 'FALSE'),"
                + "    (6, 'pyöräily', 'bike', 'TRUE'),"
                + "    (7, 'jääkiekko', 'hockey', 'FALSE'),"
                + "    (8, 'jalkapallo', 'football', 'FALSE'),"
                + "    (9, 'tennis', 'tennis', 'FALSE')"
                + ";").executeUpdate();
        connection.close();
    }
    
    /**
    * Method to clear the named database table.
    * 
    * @param    table  The name of the database table to clear.
    * @throws   SQLException if there is an error when clearing the database table.
    */
    public void clearDatabase(String table) throws SQLException {
        Connection connection = getConnection();
        connection.prepareStatement("DROP TABLE " + table + " IF EXISTS;").executeUpdate();
        connection.close();
    }
    
}
