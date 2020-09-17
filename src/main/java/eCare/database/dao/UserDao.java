package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(user);
        transaction2.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(user);
        transaction2.commit();
        session.close();
    }

    public void delete(User user){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        user.setActive(false);
        session.update(user);
        transaction.commit();
        session.close();
    }


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





}
