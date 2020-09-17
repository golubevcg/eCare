package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.Tariff;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TarifDao {

    public void save(Tariff tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(tarif);
        transaction2.commit();
        session.close();
    }

    public void update(Tariff tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(tarif);
        transaction2.commit();
        session.close();
    }

    public void delete(Tariff tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        tarif.setActive(false);
        session.update(tarif);
        transaction2.commit();
        session.close();
    }


    public List<Tariff> getTarifByTarifName(String tarifName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Tariff> listOfTarifs = session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name = :name", Tariff.class)
                .setParameter("name", tarifName).list();

        transaction.commit();
        session.close();

        return listOfTarifs;
    }

}