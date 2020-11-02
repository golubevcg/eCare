package eCare.services.api;

import eCare.model.dto.ContractDTO;

import java.util.List;
import java.util.Map;

public interface CartService {
    void compareContractInDBWithContractInSession(ContractDTO contractDTOFromCart,
                                                  List<ContractDTO> onlyContractsChanges,
                                                  Boolean showBlockedOnPage,
                                                  Map<String, Map<String, String>> mapOfOptionsEnabledDisabled);
}
