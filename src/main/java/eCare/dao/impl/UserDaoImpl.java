package eCare.dao.impl;

import eCare.HibernateSessionFactoryUtil;
import eCare.dao.interf.UserDao;
import eCare.model.Role;
import eCare.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
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
        session.save(user);
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

    public void checkUserRoles(User user, Session session){
        try {
            Set<Role> allRolesSet = new HashSet<>(session.createQuery("select r from Role r", Role.class)
                    .getResultList());
            Set<Role> userRolesCheckedSet = new HashSet<>();

            if (allRolesSet.isEmpty()){
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
