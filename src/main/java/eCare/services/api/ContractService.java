package eCare.services.api;

import eCare.model.dto.ContractDTO;
import eCare.model.enitity.Contract;

import java.util.List;

public interface ContractService {
     void save(Contract contract);
     void update(Contract contract);
     void delete(Contract contract);
     List<Contract> getContractByNumber(String number);
     List<Contract> getContractById(Long contractID);
     List<ContractDTO> searchForContractByNumber(String number);

}
