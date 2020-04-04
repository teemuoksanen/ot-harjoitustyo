
package treeniapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import treeniapp.ui.TreeniUi;

public class Main {
    public static void main(String[] args) {
        Main.reformatDataBase();
        TreeniUi.main(args);
    }
    
    /** 
     * REMOVE COMMENTS TO RECREATE DEFAULT DATABASE STRUCTURE
     */
    private static void reformatDataBase() {
        // Remove and recreate default database structures by running this
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./treeniapp", "sa", "");) {
            
            // Users
            
            connection.prepareStatement("DROP TABLE Users IF EXISTS;").executeUpdate();
            connection.prepareStatement("CREATE TABLE Users (\n"
                    + "    username VARCHAR(15) PRIMARY KEY,\n"
                    + "    name VARCHAR(20)\n"
                    + ");").executeUpdate();
            connection.prepareStatement("INSERT INTO Users (username, name) VALUES ('testaaja', 'Testikäyttäjä');").executeUpdate();
            
            // Workouts
            
            connection.prepareStatement("DROP TABLE Workouts IF EXISTS;").executeUpdate();
            connection.prepareStatement("CREATE TABLE Workouts (\n"
                    + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"
                    + "    user VARCHAR(15),\n"
                    + "    datetime TIMESTAMP,\n"
                    + "    sport INTEGER,\n"
                    + "    duration INTEGER,\n"
                    + "    distance INTEGER,\n"
                    + "    mhr INTEGER,\n"
                    + "    notes VARCHAR(250)\n"
                    + ");").executeUpdate();
            connection.prepareStatement("INSERT INTO Workouts (datetime, user, sport, duration, distance, mhr, notes) VALUES\n"
                    + "    ('2020-02-15 12:01:01', 'testaaja', 1, 65, 1500, 125, 'Juoksua parvekkeella'),\n"
                    + "    ('2020-02-19 14:02:02', 'testaaja', 2, 35, 0, 110, 'Ulkokuntosali')\n"
                    + ";").executeUpdate();
            
            // Sports
            
            connection.prepareStatement("DROP TABLE Sports IF EXISTS;").executeUpdate();
            connection.prepareStatement("CREATE TABLE Sports (\n"
                    + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"
                    + "    name VARCHAR(15),\n"
                    + "    icon VARCHAR(20),\n"
                    + "    showdist BOOLEAN\n"
                    + ");").executeUpdate();
            connection.prepareStatement("INSERT INTO Sports (name, icon, showdist) VALUES\n"
                    + "    ('juoksu', 'running', 'TRUE'),\n"
                    + "    ('kuntosali', 'gym', 'FALSE'),\n"
                    + "    ('uinti', 'swimming', 'TRUE'),\n"
                    + "    ('koripallo', 'basketball', 'FALSE')\n"
                    + ";").executeUpdate();
            
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
