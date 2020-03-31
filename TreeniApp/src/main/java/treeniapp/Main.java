
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
    
    private static void reformatDataBase() {
        // Remove and recreate default database structures by running this
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./treeniapp", "sa", "");) {
            connection.prepareStatement("DROP TABLE Users IF EXISTS;").executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users (\n"
                    + "    username VARCHAR(15) PRIMARY KEY,\n"
                    + "    name VARCHAR(20)\n"
                    + ");").executeUpdate();
            connection.prepareStatement("INSERT INTO Users (username, name) VALUES ('testaaja', 'Testikäyttäjä');").executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
