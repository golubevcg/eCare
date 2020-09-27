package eCare.dao.impl;

import eCare.HibernateSessionFactoryUtil;
import eCare.dao.api.TarifDao;
import eCare.model.enitity.Tariff;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TariffDaoImpl implements TarifDao {

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
    public List<Tariff> getTariffByTariffName(String tarifName) {
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

    @Override
    public List<Tariff> getAllTariffs() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Tariff> listOfTarifs = session.createQuery(
                "select t from Tariff t", Tariff.class).list();
        transaction.commit();
        session.close();
        return listOfTarifs;
    }

    @Transactional
    @Override
    public List<Tariff> getActiveTariffs() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Tariff> listOfTarifs = session.createQuery(
                "select t from Tariff t where t.isActive=true", Tariff.class).list();
        transaction.commit();
        session.close();
        return listOfTarifs;
    }



}