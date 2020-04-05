
package treeniapp.dao;

import java.util.List;
import treeniapp.domain.User;

public interface UserDao {
    List<User> getAll();
    User findByUsername(String username);
    User create(User user);
}