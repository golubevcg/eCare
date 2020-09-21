package eCare;


import eCare.dao.impl.UserDaoImpl;
import eCare.model.Role;
import eCare.model.User;
import eCare.services.impl.UserServiceImpl;
import eCare.services.interf.UserService;

import java.util.Collection;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setActive(true);

        Role role = new Role("USER");

        user.addRole(role);

        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.save(user);


    }
}
