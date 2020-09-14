package eCare.database.dao;

import eCare.database.entities.Contract;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContractDao {
    public void save(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(contract);
        transaction2.commit();
        session.close();
    }

    public void update(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(contract);
        transaction2.commit();
        session.close();
    }

    public void delete(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.delete(contract);
        transaction2.commit();
        session.close();
    }
}
