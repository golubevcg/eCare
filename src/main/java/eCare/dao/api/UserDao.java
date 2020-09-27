package eCare.dao.api;

import eCare.model.enitity.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    void update(User user);
    void delete(User user);
    List<User> getUserByLogin(String login);
    List<User> getUserDTOByPassportInfo(Long passwordInfo);
}
