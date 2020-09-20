package eCare.dao.interf;

import eCare.model.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    void update(User user);
    void delete(User user);
    List<User> getUserByLogin(String login);
}
