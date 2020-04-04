
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.Sport;

public interface SportDao {

    Sport create(Sport sport) throws Exception;
    Sport findById(int id);
    List<Sport> getAll();
    
}
