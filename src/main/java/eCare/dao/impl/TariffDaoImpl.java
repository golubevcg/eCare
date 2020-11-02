package eCare.dao.impl;

import eCare.dao.api.TariffDao;
import eCare.model.entity.Tariff;
import eCare.mq.MessageSender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TariffDaoImpl implements TariffDao {

    private final SessionFactory sessionFactory;

    final
    MessageSender messageSender;

    public TariffDaoImpl(SessionFactory sessionFactory, MessageSender messageSender) {
        this.sessionFactory = sessionFactory;
        this.messageSender = messageSender;
    }

    @Override
    public void save(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(tariff);
    }

    @Override
    public void update(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tariff);
    }

    @Override
    public void delete(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        tariff.setActive(false);
        session.update(tariff);
    }

    @Override
    public List<Tariff> getTariffByTariffName(String tariffName) {

        Session session = sessionFactory.getCurrentSession();
        List<Tariff> listOfTariffs = session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name = :name", Tariff.class)
                .setParameter("name", tariffName).list();

        return listOfTariffs;
    }

    @Override
    public List<Tariff> getAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        List<Tariff> listOfTarifs = session.createQuery(
                "select t from Tariff t", Tariff.class).list();
        return listOfTarifs;
    }

    @Override
    public List<Tariff> getActiveTariffs() {
        Session session = sessionFactory.getCurrentSession();
        List<Tariff> listOfTariffs = session.createQuery(
                "select t from Tariff t where t.isActive=true", Tariff.class).list();
        return listOfTariffs;
    }

    @Override
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