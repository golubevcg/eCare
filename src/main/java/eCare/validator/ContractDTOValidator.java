package eCare.validator;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.UserContractDTO;
import eCare.model.enitity.User;
import eCare.services.impl.ContractServiceImpl;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ContractDTOValidator implements Validator {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    ContractServiceImpl contractServiceImpl;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ContractDTO contract = (ContractDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractNumber", "Required");

        if(contractServiceImpl.getContractByNumber(contract.getContractNumber()).size()>0){
            errors.rejectValue("contractNumber", "Duplicate.contractDTO.contractNumber");
        }


    }
}
