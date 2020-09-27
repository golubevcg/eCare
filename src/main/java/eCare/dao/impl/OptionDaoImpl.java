package eCare.dao.impl;

import eCare.HibernateSessionFactoryUtil;
import eCare.dao.api.OptionDao;
import eCare.model.enitity.Option;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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
}
