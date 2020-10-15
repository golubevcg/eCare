package eCare.dao.impl;

import eCare.model.HibernateSessionFactoryUtil;
import eCare.dao.api.OptionDao;
import eCare.model.enitity.Option;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionDaoImpl implements OptionDao {

    @Override
    public void save(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(option);
        transaction2.commit();
        session.close();
    }

    @Override
    public void update(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(option);
        transaction2.commit();
        session.close();
    }

    @Override
    public void delete(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        option.setActive(false);
        session.update(option);
        transaction2.commit();
        session.close();
    }

    @Override
    public List<Option> getOptionByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Option> optionsList = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.name = :nam", Option.class)
                .setParameter("nam", name).list();

        transaction.commit();
        session.close();

        return optionsList;
    }

    @Override
    public List<Option> getOptionById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Option> optionsList = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.option_id = :id", Option.class)
                .setParameter("id", id).list();

        transaction.commit();
        session.close();

        return optionsList;
    }

    @Override
    public List<Option> searchForOptionByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Option> contractsList = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.name like:string", Option.class)
                .setParameter("string", "%" + name + "%")
                .list();
        transaction.commit();
        session.close();
        return contractsList;
    }

    @Override
    public List<Option> getActiveOptions() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Option> listOfTariffs = session.createQuery(
                "select o from Option o where o.isActive=true", Option.class).list();
        transaction.commit();
        session.close();
        return listOfTariffs;
    }
}
