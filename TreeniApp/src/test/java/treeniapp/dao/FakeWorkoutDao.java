
package treeniapp.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import treeniapp.domain.User;
import treeniapp.domain.Workout;

public class FakeWorkoutDao implements WorkoutDao {
    
    List<Workout> workouts = new ArrayList<>();
    UserDao userDao = new FakeUserDao();
    SportDao sportDao = new FakeSportDao();
    
    
    public FakeWorkoutDao() {
        workouts.add(new Workout(1, new Timestamp(1581768061), userDao.findByUsername("testi"), sportDao.findById(1), 65, 1500, 125, "Juoksua parvekkeella"));
        workouts.add(new Workout(2, new Timestamp(1582120922), userDao.findByUsername("testi2"), sportDao.findById(2), 35, 0, 110, "Ulkokuntosali"));
    }

    @Override
    public Workout create(Workout workout) {
        workouts.add(workout);
        return workout;
    }

    @Override
    public Workout findById(int id) {
        return workouts.stream().filter(w->w.getId()==id).findFirst().orElse(null);
    }

    @Override
    public List<Workout> getAll() {
        return workouts;
    }

    @Override
    public List<Workout> getAllByUser(User user) {
        List<Workout> filtered = new ArrayList<>();
        for (Workout w : workouts) {
            if (w.getUser().equals(user)) {
                filtered.add(w);
            }
        }
        return filtered;
    }

    @Override
    public boolean remove(Workout workout) {
        if (workouts.contains(workout)) {
            workouts.remove(workout);
        return true;
        }
        return false;
    }
    
}
