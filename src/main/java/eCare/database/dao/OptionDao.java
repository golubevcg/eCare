package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.Contract;
import eCare.database.entities.Option;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OptionDao {
    public void save(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(option);
        transaction2.commit();
        session.close();
    }

    public void update(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(option);
        transaction2.commit();
        session.close();
    }

    public void delete(Option option) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        option.setActive(false);
        session.update(option);
        transaction2.commit();
        session.close();
    }

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
}
