package eCare.dao.impl;

import eCare.dao.api.UserDao;
import eCare.model.entity.Role;
import eCare.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        this.checkUserRoles(user, session);
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        session.merge(user);
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void delete(User user){
        Session session = sessionFactory.getCurrentSession();
        user.setActive(false);
        session.update(user);
    }


    @Override
    public List<User> getUserByLogin(String login){
        Session session = sessionFactory.getCurrentSession();
        List<User> listOfUsers = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.login = :login", User.class)
                .setParameter( "login", login )
                .list();
        return listOfUsers;
    }

    @Override
    public List<User> getUserDTOByPassportInfo(String passportInfo) {
        Session session = sessionFactory.getCurrentSession();
        List<User> listOfUsers = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.passportInfo = :passportInfo", User.class)
                .setParameter( "passportInfo", passportInfo )
                .list();
        return listOfUsers;
    }

    @Override
    public List<User> searchForUserByLogin(String searchInput) {
        Session session = sessionFactory.getCurrentSession();
        List<User> usersList = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.login like:string", User.class)
                .setParameter("string", "%" + searchInput + "%")
                .list();
        return usersList;
    }

    @Override
    public List<User> getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        List<User> listOfUsers = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.email = :email", User.class)
                .setParameter( "email", email )
                .list();
        return listOfUsers;
    }

    @Override
    public void checkUserRoles(User user, Session session){
        try {
            Set<Role> allRolesSet = new HashSet<>(session.createQuery("select r from Role r", Role.class)
                    .getResultList());
            Set<Role> userRolesCheckedSet = new HashSet<>();

            if(allRolesSet.isEmpty()){
            }else{
                for (Role role: user.getRoles()) {
                    Role foundedRole = session.createQuery("select r from Role r where r.rolename = :roleName", Role.class)
                            .setParameter("roleName", role.getRolename()).getSingleResult();
                    if(null==foundedRole){
                        userRolesCheckedSet.add(role);
                    }{
                        userRolesCheckedSet.add(foundedRole);
                    }
                }
                user.setRoles(userRolesCheckedSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
