
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.Workout;
import treeniapp.domain.User;

public interface WorkoutDao {

    Workout create(Workout workout) throws Exception;

    List<Workout> getAllByUsername(User user);

}