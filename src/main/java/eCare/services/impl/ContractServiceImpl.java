package eCare.services.impl;

import eCare.dao.impl.ContractDaoImpl;
import eCare.model.Contract;
import eCare.services.interf.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractDaoImpl contractDaoImpl;

    @Override
    public void save(Contract contract) {
        contractDaoImpl.save(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDaoImpl.update(contract);
    }

    @Override
    public void delete(Contract contract) {
        contractDaoImpl.delete(contract);
    }

    @Override
    public List<Contract> getContractByNumber(String number) {
        return contractDaoImpl.getContractByNumber(number);
    }

}
