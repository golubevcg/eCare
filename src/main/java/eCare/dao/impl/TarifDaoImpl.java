package eCare.dao.impl;

import eCare.HibernateSessionFactoryUtil;
import eCare.dao.interf.TarifDao;
import eCare.model.Tariff;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarifDaoImpl implements TarifDao {

    @Override
    public void save(Tariff tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(tarif);
        transaction2.commit();
        session.close();
    }

    @Override
    public void update(Tariff tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(tarif);
        transaction2.commit();
        session.close();
    }

    @Override
    public void delete(Tariff tarif) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        tarif.setActive(false);
        session.update(tarif);
        transaction2.commit();
        session.close();
    }

    @Override
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