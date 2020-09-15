package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.Contract;
import eCare.database.entities.Tarif;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TarifDao {

    public void save(Tarif tarif) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction transaction2 = session.beginTransaction();
    session.save(tarif);
    transaction2.commit();
    session.close();
    }

    public void update(Tarif tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(tarif);
        transaction2.commit();
        session.close();
    }

    public void delete(Tarif tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.delete(tarif);
        transaction2.commit();
        session.close();
    }
}
