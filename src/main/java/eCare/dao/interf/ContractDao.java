package eCare.dao.interf;

import eCare.model.Contract;

import java.util.List;

public interface ContractDao {
    void save(Contract contract);
    void update(Contract contract);
    void delete(Contract contract);
    List<Contract> getContractByNumber(String number);
}
