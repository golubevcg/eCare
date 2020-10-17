package eCare.dao.impl;

import eCare.dao.api.ContractDao;
import eCare.model.entity.Contract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ContractDaoImpl implements ContractDao {

    private final SessionFactory sessionFactory;

    public ContractDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(contract);
    }

    @Override
    @Transactional
    public void update(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.update(contract);
    }

    @Override
    @Transactional
    public void delete(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        contract.setActive(false);
        session.update(contract);
    }

    @Override
    @Transactional
    public List<Contract> searchForContractByNumber(String number){
        Session session = sessionFactory.getCurrentSession();
        List<Contract> contractsList = session.createQuery(
                "select c " +
                        "from Contract c " +
                        "where c.contractNumber like:string", Contract.class)
                .setParameter("string", "%" + number + "%")
                .list();
        return contractsList;
    }

    @Override
    @Transactional
    public List<Contract> getContractByNumber(String number) {
        Session session = sessionFactory.getCurrentSession();
        List<Contract> contractsList = session.createQuery(
                "select c " +
                        "from Contract c " +
                        "where c.contractNumber = :num", Contract.class)
                .setParameter("num", number).list();
        return contractsList;
    }

    @Override
    @Transactional
    public List<Contract> getContractById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        List<Contract> contractsList = session.createQuery(
                "select c " +
                        "from Contract c " +
                        "where c.contract_id = :id", Contract.class)
                .setParameter("id", id).list();
        return contractsList;
    }
}
