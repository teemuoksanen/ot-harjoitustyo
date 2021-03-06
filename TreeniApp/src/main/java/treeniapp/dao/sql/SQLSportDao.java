
package treeniapp.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import treeniapp.dao.SportDao;
import treeniapp.domain.Sport;

/**
 * Class contains methods to get and store <code>Sport</code> objects from and into SQL database.
 */
public class SQLSportDao implements SportDao {
    
    private SQLService sql;
    private List<Sport> sports;
    private Map<Integer, Sport> sportMap;
    
    /**
    * Constructor for <code>SQLSportDao</code> with the <code>SQLService</code> injected.
    *
    * @param sql       <code>SQLService</code> class.
    * @throws java.lang.Exception if the SQL query leads to an error.
    */
    public SQLSportDao(SQLService sql) throws Exception {
        this.sql = sql;
        this.sports = new ArrayList<>();
        this.sportMap = new HashMap<>();
        
        getSports();
    }
    
    /**
    * Method to initially fetch all sports from the database and stored to an ArrayList and a HashMap.
    */
    private void getSports() throws SQLException {
        
        this.sports.clear();
        this.sportMap.clear();
        
        Connection connection = sql.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Sports ORDER BY name");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String icon = rs.getString("icon");
            Boolean showDistance = rs.getBoolean("showdist");
            Sport sport = new Sport(id, name, icon, showDistance);
            sports.add(sport);
            sportMap.put(id, sport);
        }

        connection.close();
    }

    /**
    * Method to store a new sport to the database.
    * 
    * @param    sport   The <code>Sport</code> object to be stored to the database.
    * 
    * @return <code>Sport</code> object that was stored to the database.
    */
    @Override
    public Sport create(Sport sport) throws SQLException {
        Connection connection = sql.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Sports"
            + " (name, icon, showdist) VALUES (?, ?, ?)");
        stmt.setString(1, sport.getName());
        stmt.setString(2, sport.getIcon());
        stmt.setBoolean(3, sport.isShowDistance());
        stmt.executeUpdate();
        stmt.close();
        connection.close();

        getSports();

        return sport;
    }

    /**
    * Method to find a sport by its id number.
    * 
    * @param    id   The id number of the <code>Sport</code> object to be fetched.
    * 
    * @return <code>Sport</code> object with the named id; <code>null</code> if not found.
    */
    @Override
    public Sport findById(int id) {
        return sportMap.getOrDefault(id, null);
    }

    /**
    * Method to find a sport by its name.
    * 
    * @param    name   The name of the <code>Sport</code> object to be fetched.
    * 
    * @return <code>Sport</code> object with the name; <code>null</code> if not found.
    */
    @Override
    public Sport findByName(String name) {
        for (Sport s : sports) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    /**
    * Method to list all sports.
    * 
    * @return ArrayList containing all <code>Sport</code> objects.
    */
    @Override
    public List<Sport> getAll() {
        return sports;
    }
    
}
