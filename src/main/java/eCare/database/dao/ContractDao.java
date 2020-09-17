package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.Contract;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ContractDao {
    public void save(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(contract);
        transaction2.commit();
        session.close();
    }

    public void update(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(contract);
        transaction2.commit();
        session.close();
    }

    public void delete(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        contract.setActive(false);
        session.update(contract);
        transaction2.commit();
        session.close();
    }

    public List<Contract> getContractByNumber(String number) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Contract> contractsList = session.createQuery(
                "select c " +
                        "from Contract c " +
                        "where c.contractNumber = :num", Contract.class)
                .setParameter("num", number).list();

        transaction.commit();
        session.close();

        return contractsList;
    }
}
