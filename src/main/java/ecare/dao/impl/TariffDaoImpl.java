package ecare.dao.impl;

import ecare.dao.api.TariffDao;
import ecare.model.entity.Tariff;
import ecare.mq.MessageSender;
import ecare.services.impl.TariffServiceImpl;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TariffDaoImpl implements TariffDao {

    static final Logger log = Logger.getLogger(TariffServiceImpl.class);

    private final SessionFactory sessionFactory;

    private String tariffWithName = "Tariff with name=";

    final
    MessageSender messageSender;

    public TariffDaoImpl(SessionFactory sessionFactory, MessageSender messageSender) {
        this.sessionFactory = sessionFactory;
        this.messageSender = messageSender;
    }

    @Override
    public void save(Tariff tariff) {
    try{
        Session session = sessionFactory.getCurrentSession();
        session.persist(tariff);
        log.info(tariffWithName + tariff.getName() + " was successfully saved!");
    }catch(Exception e){
        log.info("There was an error during saving tariff with name=" + tariff.getName());
    }

    }

    @Override
    public void update(Tariff tariff) {
        try{
            Session session = sessionFactory.getCurrentSession();
            session.update(tariff);
            log.info(tariffWithName + tariff.getName() + " was successfully updated!");
        }catch(Exception e){
            log.info("There was an error during updating tariff with name=" + tariff.getName());
        }
    }

    @Override
    public void delete(Tariff tariff) {
        try{
            Session session = sessionFactory.getCurrentSession();
            tariff.setActive(false);
            session.update(tariff);
            log.info(tariffWithName + tariff.getName() + " was successfully deleted!");
        }catch(Exception e){
            log.info("There was an error during deleting tariff with name=" + tariff.getName());
        }
    }

    @Override
    public List<Tariff> getTariffByTariffName(String tariffName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Tariff> query = session
                .createQuery("select t from Tariff t where t.name = :name", Tariff.class);
        query.setParameter("name", tariffName);
        return query.list();
    }

    @Override
    public List<Tariff> getAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        Query<Tariff> query = session.createQuery(
                "select t from Tariff t", Tariff.class);
        return query.list();
    }

    @Override
    public List<Tariff> getActiveTariffs() {
        Session session = sessionFactory.getCurrentSession();
        Query<Tariff> query = session.createQuery(
                "select t from Tariff t where t.isActive=true", Tariff.class);
        return query.list();
    }

    @Override
    public List<Tariff> searchForTariffByName(String name){
        Session session = sessionFactory.getCurrentSession();
        Query<Tariff> query = session.createQuery(
                "select t from Tariff t where t.name like:string", Tariff.class);
        query.setParameter("string", "%" + name + "%");
        return query.list();
    }

}