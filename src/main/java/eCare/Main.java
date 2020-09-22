package eCare;


import eCare.dao.impl.UserDaoImpl;
import eCare.model.enitity.Role;
import eCare.model.enitity.User;

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
