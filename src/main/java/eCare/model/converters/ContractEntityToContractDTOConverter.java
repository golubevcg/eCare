package eCare.model.converters;

import eCare.model.dto.ContractDTO;
import eCare.model.enitity.Contract;
import eCare.services.impl.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class ContractEntityToContractDTOConverter{

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @Autowired
    UserEntityToUserDTOConverter userEntityToUserDTOConverter;



    public ContractDTO convertToDto(Contract contract){

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(contract.getContractNumber());
        contractDTO.setBlocked(contract.isBlocked());
        contractDTO.setUser(userEntityToUserDTOConverter.convertToDto( contract.getUser()) );
        contractDTO.setTariff(contract.getTariff());
        contractDTO.setActive(contract.isActive());

        return contractDTO;
    }


    public Contract convertDTOtoEntity(ContractDTO contractDTO){

        Contract contract = contractServiceImpl.getContractByNumber(contractDTO.getContractNumber()).get(0);
        contract.setContractNumber(contractDTO.getContractNumber());
        contract.setBlocked(contractDTO.isBlocked());
        contract.setUser(userEntityToUserDTOConverter.convertDTOtoEntity( contractDTO.getUser() ));
        contract.setTariff(contractDTO.getTariff());
        contract.setActive(contractDTO.isActive());

        return contract;
    }

}
