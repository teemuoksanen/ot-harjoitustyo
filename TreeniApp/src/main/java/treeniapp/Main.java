
package treeniapp;

import treeniapp.ui.TreeniUi;

public class Main {
    public static void main(String[] args) {
        TreeniUi.main(args);
    }
    
    private static void reformatDataBase() {
        // Remove and recreate default database structures by running this
 
        /* try (Connection conn = DriverManager.getConnection("jdbc:h2:./treeniapp", "sa", "")) {
            conn.prepareStatement("DROP TABLE Users IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS Users (\n"
                    + "    username VARCHAR(15) PRIMARY KEY,\n"
                    + "    name VARCHAR(20)\n"
                    + ");").executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } */
    }
}
