package eCare.services.api;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.entity.Contract;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public interface ContractService {
     void save(Contract contract);
     void update(Contract contract);
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
     boolean submitValuesFromController(String exportArray, String contractNumberBeforeEditing);

     String getDependingOptions(HttpSession session, String selectedOptionId, String[] stringsArrayInfoFromFront);

     String getSortedListOfOptions(String contractNumber, String selectedTariffName, HttpSession session);

     void addContractDetailsToModelForPage(Model model, String contractID,
                                           HttpSession session);
}
