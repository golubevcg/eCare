package ecare.services.api;

import ecare.model.dto.ContractDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public interface CartService {
    void compareContractInDBWithContractInSession(ContractDTO contractDTOFromCart,
                                                  List<ContractDTO> onlyContractsChanges,
                                                  AtomicBoolean showBlockedOnPage,
                                                  Map<String, Map<String, String>> mapOfOptionsEnabledDisabled);
}
