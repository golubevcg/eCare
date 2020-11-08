package ecare.dao.impl;

import ecare.dao.api.TariffDao;
import ecare.model.entity.Tariff;
import ecare.mq.MessageSender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        return session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name = :name", Tariff.class)
                .setParameter("name", tariffName).list();
    }

    @Override
    public List<Tariff> getAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        return  session.createQuery(
                "select t from Tariff t", Tariff.class).list();
    }

    @Override
    public List<Tariff> getActiveTariffs() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                "select t from Tariff t where t.isActive=true", Tariff.class).list();
    }

    @Override
    public List<Tariff> searchForTariffByName(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                "select t " +
                        "from Tariff t " +
                        "where t.name like:string", Tariff.class)
                .setParameter("string", "%" + name + "%")
                .list();
    }

}