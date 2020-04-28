
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.Workout;
import treeniapp.domain.User;

public interface WorkoutDao {

    Workout create(Workout workout) throws Exception;
    Workout update(Workout workout) throws Exception;
    Workout findById(int id) throws Exception;
    List<Workout> getAll() throws Exception;
    List<Workout> getAllByUser(User user) throws Exception;

}