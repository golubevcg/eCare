package eCare.dao.api;

import eCare.model.enitity.Contract;

import java.util.List;

public interface ContractDao {
    void save(Contract contract);
    void update(Contract contract);
    void delete(Contract contract);
    List<Contract> getContractByNumber(String number);
}
