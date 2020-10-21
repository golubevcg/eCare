package eCare.services.api;

import eCare.model.dto.ContractDTO;
import eCare.model.entity.Contract;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContractService {
     void save(Contract contract);
     void update(Contract contract);

    @Transactional
    void updateConvertDTO(ContractDTO contractDTO);

    void delete(Contract contract);
     List<Contract> getContractByNumber(String number);
     List<Contract> getContractById(Long contractID);
     List<ContractDTO> searchForContractByNumber(String number);
     Contract convertDTOtoEntity(ContractDTO contractDTO);
     void convertToEntityAndSave(ContractDTO contractDTO);
     void convertToEntityAndUpdate(ContractDTO contractDTO);
     List<ContractDTO> getContractDTOByNumber(String number);
     ContractDTO getContractDTOByNumberOrNull(String number);
     List<ContractDTO> getContractDTOById(Long contractID);
}
