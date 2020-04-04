
package treeniapp.domain;

import java.util.ArrayList;
import java.util.List;
import treeniapp.dao.SportDao;

public class FakeSportDao implements SportDao {
    
    List<Sport> sports;
    
    public FakeSportDao(String databaseDB, String usernameDB, String passwordDB) throws Exception {
        this.sports = new ArrayList<>();
        create(new Sport(1, "juoksu", "running", true));
        create(new Sport(2, "kuntosali", "gym", false));
        create(new Sport(3, "uinti", "swimming", true));
        create(new Sport(4, "koripallo", "basketball", false));
    }

    @Override
    public Sport create(Sport sport) throws Exception {
        sports.add(sport);
        return sport;
    }

    @Override
    public Sport findById(int id) {
        for (Sport sport : sports) {
            if (sport.getId() == id) {
                return sport;
            }
        }
        return null;
    }

    @Override
    public List<Sport> getAll() {
        //Not supported yet
        return sports;
    }
    
}
