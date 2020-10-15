package eCare.dao.impl;

import eCare.model.HibernateSessionFactoryUtil;
import eCare.dao.api.UserDao;
import eCare.model.enitity.Role;
import eCare.model.enitity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDaoImpl implements UserDao {

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        this.checkUserRoles(user, session);
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        session.merge(user);
        transaction2.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(user);
        transaction2.commit();
        session.close();
    }

    @Override
    public void delete(User user){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        user.setActive(false);
        session.update(user);
        transaction.commit();
        session.close();
    }


    @Override
    public List<User> getUserByLogin(String login){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<User> listOfUsers = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.login = :login", User.class)
                .setParameter( "login", login )
                .list();

        transaction.commit();
        session.close();
        return listOfUsers;
    }

    @Override
    public List<User> getUserDTOByPassportInfo(Long passportInfo) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<User> listOfUsers = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.passportInfo = :passportInfo", User.class)
                .setParameter( "passportInfo", passportInfo )
                .list();

        transaction.commit();
        session.close();
        return listOfUsers;
    }

    @Override
    public List<User> searchForUserByLogin(String searchInput) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> usersList = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.login like:string", User.class)
                .setParameter("string", "%" + searchInput + "%")
                .list();
        session.close();
        return usersList;
    }

    @Override
    public List<User> getUserByEmail(String email) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<User> listOfUsers = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.email = :email", User.class)
                .setParameter( "email", email )
                .list();

        transaction.commit();
        session.close();
        return listOfUsers;
    }


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
