package eCare.database.services;

import eCare.database.dao.UserDao;
import eCare.database.entities.User;

public class UserService {

    UserDao userDao = new UserDao();

    public void save(User user){
        userDao.save(user);
    }

    public void update(User user){
        userDao.update(user);
    }

    public void delete(User user){
        userDao.delete(user);
    }

}
