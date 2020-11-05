package ecare.dao.api;

import ecare.model.entity.Contract;

import java.util.List;

public interface ContractDao {
    void save(Contract contract);
    void update(Contract contract);
    void delete(Contract contract);
    List<Contract> getContractByNumber(String number);
    List<Contract> getContractById(Long id);
    List<Contract> searchForContractByNumber(String number);

}
