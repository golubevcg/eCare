package eCare.validator;

import eCare.model.dto.ContractDTO;
import eCare.services.api.ContractService;
import eCare.services.impl.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ContractValidator implements Validator{

    @Autowired
    ContractService contractService = new ContractServiceImpl();

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ContractDTO contractDTO = (ContractDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractNumber", "Required");
        if(!contractDTO.getContractNumber().matches("[+]*([0-9]{11})")){
            errors.rejectValue("contractNumber", "Pattern.contractDTO.contractNumber");
        }

        if(contractService.getContractByNumber(contractDTO.getContractNumber() ).size()>0){
            errors.rejectValue("contractNumber", "Duplicate.contractDTO.contractNumber");
        }

    }
}
