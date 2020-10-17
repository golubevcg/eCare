package eCare.dao.impl;

import eCare.dao.api.TariffDao;
import eCare.model.entity.Tariff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TariffDaoImpl implements TariffDao {

    private final SessionFactory sessionFactory;

    public TariffDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(tariff);
    }

    @Override
    @Transactional
    public void update(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tariff);
    }

    @Override
    @Transactional
    public void delete(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        tariff.setActive(false);
        session.update(tariff);
    }

    @Override
    @Transactional
    public List<Tariff> getTariffByTariffName(String tariffName) {
        Session session = sessionFactory.getCurrentSession();
        List<Tariff> listOfTarifs = session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name = :name", Tariff.class)
                .setParameter("name", tariffName).list();
        return listOfTarifs;
    }

    @Override
    @Transactional
    public List<Tariff> getAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        List<Tariff> listOfTarifs = session.createQuery(
                "select t from Tariff t", Tariff.class).list();
        return listOfTarifs;
    }

    @Override
    @Transactional
    public List<Tariff> getActiveTariffs() {
        Session session = sessionFactory.getCurrentSession();
        List<Tariff> listOfTariffs = session.createQuery(
                "select t from Tariff t where t.isActive=true", Tariff.class).list();
        return listOfTariffs;
    }

    @Override
    @Transactional
    public List<Tariff> searchForTariffByName(String name){
        Session session = sessionFactory.getCurrentSession();
        List<Tariff> contractsList = session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name like:string", Tariff.class)
                .setParameter("string", "%" + name + "%")
                .list();
        return contractsList;
    }



}