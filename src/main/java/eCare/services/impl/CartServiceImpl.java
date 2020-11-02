package eCare.services.impl;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.services.api.CartService;
import eCare.services.api.ContractService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
                                                         Boolean showBlockedOnPage,
                                                         Map<String, Map<String, String>> mapOfOptionsEnabledDisabled){
        ContractDTO contractDTOFromDB =  contractService
                .getContractDTOByNumberOrNull(contractDTOFromCart.getContractNumber());
        ContractDTO editedContractDTO = new ContractDTO();
        editedContractDTO.setContractNumber(contractDTOFromDB.getContractNumber());
        boolean isChanged = false;

        if (!contractDTOFromCart.getTariff().equals(contractDTOFromDB.getTariff())) {
            editedContractDTO.setTariff(contractDTOFromCart.getTariff());
            isChanged = true;
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
            isChanged = true;
            showBlockedOnPage = true;
        }

        if (isChanged) {
            onlyContractsChanges.add(editedContractDTO);
        }
    }

    private void checkNewOptionsInEditedContract(Set<OptionDTO> optionDTOSetFromCart,
                                                 Set<OptionDTO> optionDTOSetFromDB,
                                                 Boolean isChanged,
                                                 Map<String, String> optionMapEnabledDisabled){
        for (OptionDTO optionDTOFromSession : optionDTOSetFromCart) {
            if (!optionDTOSetFromDB.contains(optionDTOFromSession)
                    && optionDTOSetFromCart.contains(optionDTOFromSession)) {
                optionMapEnabledDisabled.put(optionDTOFromSession.getName(), "enabled");
                isChanged = true;
            }
        }
    }


    private void checkRemovedOptionsInEditedContract(Set<OptionDTO> optionDTOSetFromCart,
                                                     Set<OptionDTO> optionDTOSetFromDB,
                                                     Boolean isChanged,
                                                     Map<String, String> optionMapEnabledDisabled){
        for (OptionDTO optionDTOFromDb : optionDTOSetFromDB) {
            if (!optionDTOSetFromCart.contains(optionDTOFromDb)
                    && optionDTOSetFromCart.contains( optionDTOSetFromDB )) {

                optionMapEnabledDisabled.put(optionDTOFromDb.getName(), "disabled");
                isChanged = true;
            }
        }
    }
}
