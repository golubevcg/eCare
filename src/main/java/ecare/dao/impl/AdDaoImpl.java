package ecare.dao.impl;

import ecare.dao.api.AdDao;
import ecare.model.entity.Ad;
import ecare.mq.MessageSender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AdDaoImpl implements AdDao {

    private final SessionFactory sessionFactory;

    final
    MessageSender messageSender;

    public AdDaoImpl(MessageSender messageSender, SessionFactory sessionFactory) {
        this.messageSender = messageSender;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Ad ad) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(ad);
    }

    @Override
    public void update(Ad ad) {
        Session session = sessionFactory.getCurrentSession();
        session.update(ad);
    }

    @Override
    public void delete(Ad ad) {
        Session session = sessionFactory.getCurrentSession();
        ad.setActive(false);
        session.update(ad);
    }

    @Override
    public Ad getAdByNameOrNull(String name){
        Session session = sessionFactory.getCurrentSession();

        Query<Ad> query = session.createQuery("SELECT a FROM Ad a WHERE a.name = :nam", Ad.class);
        query.setParameter("nam", name);
        List<Ad> adsList = query.list();

        return returnAdIfAdListEmpty( adsList);
    }

    private Ad returnAdIfAdListEmpty( List<Ad> adsList){
        if(adsList.isEmpty()) return null;
        {
            return adsList.get(0);
        }
    }

}