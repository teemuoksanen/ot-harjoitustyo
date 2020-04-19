
package treeniapp.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import treeniapp.dao.SportDao;
import treeniapp.domain.Sport;

public class SQLSportDao implements SportDao {
    
    private SQLService sql;
    private List<Sport> sports;
    private Map<Integer, Sport> sportMap;
    private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    
    public SQLSportDao(SQLService sql) {
        this.sql = sql;
        this.sports = new ArrayList<>();
        this.sportMap = new HashMap<>();
        
        getInitialSports();
    }
    
    private void getInitialSports() {
        try {
            Connection connection = sql.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Sports");
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
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Sport create(Sport sport) {
        try {
            Connection connection = sql.getConnection();

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Sports"
                + " (id, name, icon, showdist) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, sport.getId());
            stmt.setString(2, sport.getName());
            stmt.setString(3, sport.getIcon());
            stmt.setBoolean(4, sport.isShowDistance());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            
            sports.add(sport);
            sportMap.put(sport.getId(), sport);

            return sport;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Sport findById(int id) {
        return sportMap.getOrDefault(id, null);
    }

    @Override
    public List<Sport> getAll() {
        return sports;
    }
    
}
