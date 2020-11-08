package ecare.dao.impl;

import ecare.dao.api.ContractDao;
import ecare.model.entity.Contract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ContractDaoImpl implements ContractDao {

    private final SessionFactory sessionFactory;

    public ContractDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(contract);
    }

    @Override
    public void update(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.update(contract);
    }

    @Override
    public void delete(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        contract.setActive(false);
        session.update(contract);
    }

    @Override
    public List<Contract> searchForContractByNumber(String number){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Contract c " +
                "where c.contractNumber like:string", Contract.class)
                .setParameter("string", "%" + number + "%")
                .list();
    }

    @Override
    public List<Contract> getContractByNumber(String number) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Contract c " +
                "where c.contractNumber = :num", Contract.class)
                .setParameter("num", number).list();
    }

    @Override
    public List<Contract> getContractById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Contract c " +
                "where c.contract_id = :id", Contract.class)
                .setParameter("id", id).list();
    }
}
