package eCare.validator;

import eCare.model.dto.ContractDTO;
import eCare.model.entity.User;
import eCare.services.api.ContractService;
import eCare.services.api.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ContractDTOValidator implements Validator {

    final
    UserService userServiceImpl;

    final
    ContractService contractServiceImpl;

    public ContractDTOValidator(UserService userServiceImpl, ContractService contractServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.contractServiceImpl = contractServiceImpl;
    }

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
