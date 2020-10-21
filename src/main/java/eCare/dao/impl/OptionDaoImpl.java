package eCare.dao.impl;

import eCare.dao.api.OptionDao;
import eCare.model.entity.Option;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OptionDaoImpl implements OptionDao {

    private final SessionFactory sessionFactory;

    public OptionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(option);
    }

    @Override
    public void update(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.update(option);
    }

    @Override
    public void delete(Option option) {
        Session session = sessionFactory.getCurrentSession();
        option.setActive(false);
        session.update(option);
    }

    @Override
    public List<Option> getOptionByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        List<Option> optionsList = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.name = :nam", Option.class)
                .setParameter("nam", name).list();
        return optionsList;
    }

    @Override
    public List<Option> getOptionById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        List<Option> optionsList = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.option_id = :id", Option.class)
                .setParameter("id", id).list();
        return optionsList;
    }

    @Override
    public List<Option> searchForOptionByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        List<Option> contractsList = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.name like:string", Option.class)
                .setParameter("string", "%" + name + "%")
                .list();
        return contractsList;
    }

    @Override
    public List<Option> getActiveOptions() {
        Session session = sessionFactory.getCurrentSession();
        List<Option> listOfTariffs = session.createQuery(
                "select o from Option o where o.isActive=true", Option.class).list();
        return listOfTariffs;
    }
}
