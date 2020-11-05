package ecare.dao.api;

import ecare.model.entity.User;
import org.hibernate.Session;

import java.util.List;

public interface UserDao {
    void save(User user);
    void update(User user);
    void delete(User user);
    List<User> getUserByLogin(String login);
    List<User> getUserDTOByPassportInfo(String passwordInfo);
    List<User> searchForUserByLogin(String searchInput);
    List<User> getUserByEmail(String email);
    void checkUserRoles(User user, Session session);
}
