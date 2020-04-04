
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.Workout;
import treeniapp.domain.User;

public interface WorkoutDao {

    Workout create(Workout workout);
    Workout update(Workout workout);
    Workout findById(int id);
    List<Workout> getAll();
    List<Workout> getAllByUser(User user);

}