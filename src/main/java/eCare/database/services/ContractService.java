package eCare.database.services;

import eCare.database.dao.ContractDao;
import eCare.database.entities.Contract;

public class ContractService{

    ContractDao contractDao = new ContractDao();

    public void save(Contract contract) {
        contractDao.save(contract);
    }

    public void update(Contract contract) {
        contractDao.update(contract);
    }

    public void delete(Contract contract) {
        contractDao.delete(contract);
    }

}
