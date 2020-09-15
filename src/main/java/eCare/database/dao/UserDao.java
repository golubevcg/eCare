package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.delete(user);
        transaction2.commit();
        session.close();
    }
}
