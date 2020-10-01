package eCare.dao.impl;

import eCare.HibernateSessionFactoryUtil;
import eCare.dao.api.ContractDao;
import eCare.model.enitity.Contract;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContractDaoImpl implements ContractDao {

    @Override
    public void save(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(contract);
        transaction2.commit();
        session.close();
    }

    @Override
    public void update(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(contract);
        transaction2.commit();
        session.close();
    }

    @Override
    public void delete(Contract contract) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        contract.setActive(false);
        session.update(contract);
        transaction2.commit();
        session.close();
    }



    @Override
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

    @Override
    public List<Contract> getContractById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Contract> contractsList = session.createQuery(
                "select c " +
                        "from Contract c " +
                        "where c.contract_id = :id", Contract.class)
                .setParameter("id", id).list();

        transaction.commit();
        session.close();

        return contractsList;
    }
}
