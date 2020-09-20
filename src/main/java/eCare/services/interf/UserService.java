package eCare.services.interf;

import eCare.model.User;
import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    void delete(User user);
    List<User> getUserByLogin(String login);
}
