
package treeniapp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import treeniapp.domain.Sport;

public class FakeSportDao implements SportDao {
    
    List<Sport> sports;
    Map<Integer, Sport> sportMap;
    
    public FakeSportDao() {
        this.sports = new ArrayList<>();
        this.sportMap = new HashMap<>();
        create(new Sport(1, "juoksu", "running", true));
        create(new Sport(2, "kuntosali", "gym", false));
        create(new Sport(3, "uinti", "swimming", true));
        create(new Sport(4, "koripallo", "basketball", false));
    }

    @Override
    public Sport create(Sport sport) {
        sports.add(sport);
        sportMap.put(sport.getId(), sport);
        return sport;
    }

    @Override
    public Sport findById(int id) {
        return sportMap.getOrDefault(id, null);
    }

    @Override
    public List<Sport> getAll() {
        //Not supported yet
        return sports;
    }
    
}
