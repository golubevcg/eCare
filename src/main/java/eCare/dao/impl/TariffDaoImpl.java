package eCare.dao.impl;

import eCare.HibernateSessionFactoryUtil;
import eCare.dao.api.TariffDao;
import eCare.model.enitity.Contract;
import eCare.model.enitity.Tariff;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TariffDaoImpl implements TariffDao {

    @Override
    public void save(Tariff tariff) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.merge(tariff);
        transaction2.commit();
        session.close();
    }

    @Override
    public void update(Tariff tariff) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(tariff);
        transaction2.commit();
        session.close();
    }

    @Override
    public void delete(Tariff tariff) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        tariff.setActive(false);
        session.update(tariff);
        transaction2.commit();
        session.close();
    }

    @Override
    public List<Tariff> getTariffByTariffName(String tariffName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Tariff> listOfTarifs = session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name = :name", Tariff.class)
                .setParameter("name", tariffName).list();

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
        List<Tariff> listOfTariffs = session.createQuery(
                "select t from Tariff t where t.isActive=true", Tariff.class).list();
        transaction.commit();
        session.close();
        return listOfTariffs;
    }

    @Override
    public List<Tariff> searchForTariffByName(String name){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Tariff> contractsList = session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name like:string", Tariff.class)
                .setParameter("string", "%" + name + "%")
                .list();
        transaction.commit();
        session.close();

        return contractsList;
    }



}