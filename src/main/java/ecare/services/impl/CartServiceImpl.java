package ecare.services.impl;

import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.services.api.CartService;
import ecare.services.api.ContractService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class CartServiceImpl implements CartService {


    final
    ContractService contractService;

    public CartServiceImpl(ContractService contractService) {
        this.contractService = contractService;
    }

    @Override
    public void compareContractInDBWithContractInSession(ContractDTO contractDTOFromCart,
                                                         List<ContractDTO> onlyContractsChanges,
                                                         AtomicBoolean showBlockedOnPage,
                                                         Map<String, Map<String, String>> mapOfOptionsEnabledDisabled){
        ContractDTO contractDTOFromDB =  contractService
                .getContractDTOByNumberOrNull(contractDTOFromCart.getContractNumber());
        ContractDTO editedContractDTO = new ContractDTO();
        editedContractDTO.setContractNumber(contractDTOFromDB.getContractNumber());
        AtomicBoolean isChanged = new AtomicBoolean();
        isChanged.set(false);

        if (!contractDTOFromCart.getTariff().equals(contractDTOFromDB.getTariff())) {
            editedContractDTO.setTariff(contractDTOFromCart.getTariff());
            isChanged.set(true);
        }

        Map<String, String> optionMapEnabledDisabled = new HashMap<>();


        checkNewOptionsInEditedContract(contractDTOFromCart.getSetOfOptions(),
                                        contractDTOFromDB.getSetOfOptions(),
                                        isChanged,
                                        optionMapEnabledDisabled);

        checkRemovedOptionsInEditedContract(contractDTOFromCart.getSetOfOptions(),
                contractDTOFromDB.getSetOfOptions(),
                isChanged,
                optionMapEnabledDisabled);

        mapOfOptionsEnabledDisabled.put(contractDTOFromCart.getContractNumber(), optionMapEnabledDisabled);

        if (contractDTOFromCart.isBlocked() != contractDTOFromDB.isBlocked()) {
            editedContractDTO.setBlocked(contractDTOFromCart.isBlocked());
            isChanged.set(true);
            showBlockedOnPage.set(true);
        }

        if (isChanged.get()) {
            onlyContractsChanges.add(editedContractDTO);
        }

    }

    private void checkNewOptionsInEditedContract(Set<OptionDTO> optionDTOSetFromCart,
                                                 Set<OptionDTO> optionDTOSetFromDB,
                                                 AtomicBoolean isChanged,
                                                 Map<String, String> optionMapEnabledDisabled){
        for (OptionDTO optionDTOFromSession : optionDTOSetFromCart) {
            if (!optionDTOSetFromDB.contains(optionDTOFromSession)
                    && optionDTOSetFromCart.contains(optionDTOFromSession)) {
                optionMapEnabledDisabled.put(optionDTOFromSession.getName(), "enabled");
                isChanged.set(true);
            }
        }

    }


    private void checkRemovedOptionsInEditedContract(Set<OptionDTO> optionDTOSetFromCart,
                                                     Set<OptionDTO> optionDTOSetFromDB,
                                                     AtomicBoolean isChanged,
                                                     Map<String, String> optionMapEnabledDisabled){
        for (OptionDTO optionDTOFromDb : optionDTOSetFromDB) {
            if (!optionDTOSetFromCart.contains(optionDTOFromDb)
                    && optionDTOSetFromCart.contains( optionDTOSetFromDB )) {
                optionMapEnabledDisabled.put(optionDTOFromDb.getName(), "disabled");
                isChanged.set(true);
            }
        }
    }
}
