package eCare.services.impl;

import eCare.dao.impl.RoleDaoImpl;
import eCare.dao.impl.UserDaoImpl;
import eCare.model.Role;
import eCare.model.User;
import eCare.services.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {

    @Autowired
    UserDaoImpl userDaoImpl;

    @Autowired
    RoleDaoImpl roleDaoImpl;

    @Override
    public void save(User user){
        userDaoImpl.save(user);
    }

    @Override
    public void update(User user){
        userDaoImpl.update(user);
    }

    @Override
    public void delete(User user) { userDaoImpl.delete(user); }

    @Override
    public List<User> getUserByLogin(String login){
            return userDaoImpl.getUserByLogin(login);
    }

}
