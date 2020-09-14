package eCare.database.dao;

import eCare.database.entities.Contract;

public class OptionDao {    
    public void save(Option option) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction transaction2 = session.beginTransaction();
    session.save(option);
    transaction2.commit();
    session.close();
}

    public void update(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(contract);
        transaction2.commit();
        session.close();
    }

    public void delete(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.delete(option);
        transaction2.commit();
        session.close();
    }
}
