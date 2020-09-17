package eCare.database.services;

import eCare.database.dao.UserDao;
import eCare.database.entities.Contract;
import eCare.database.entities.User;

import java.util.List;

public class UserService {

    UserDao userDao = new UserDao();

    public void save(User user){
        userDao.save(user);
    }

    public void update(User user){
        userDao.update(user);
    }

    public void delte(User user) { userDao.delete(user); }

    public List<User> getUserByLogin(String login){
            return userDao.getUserByLogin(login);
    }

}
