package eCare.services.api;

import eCare.model.enitity.Contract;

import java.util.List;
import java.util.Map;

public interface ContractService {
     void save(Contract contract);
     void update(Contract contract);
     void delete(Contract contract);
     List<Contract> getContractByNumber(String number);
     List<Contract> getContractById(Long contractID);
}
