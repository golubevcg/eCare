package eCare.validator;

import eCare.model.dto.OptionDTO;
import eCare.model.dto.UserContractDTO;
import eCare.model.enitity.Option;
import eCare.model.enitity.User;
import eCare.services.api.OptionService;
import eCare.services.impl.ContractServiceImpl;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OptionDTOValidator implements Validator {

    @Autowired
    OptionService optionServiceImpl;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OptionDTO option = (OptionDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "connectionCost", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortDiscription", "Required");

        if(optionServiceImpl.getOptionByName(option.getName()).size()>0){
            errors.rejectValue("name", "Duplicate.optionDTO.name");
        }

        if(option.getShortDiscription().length()<8){
            errors.rejectValue("shortDiscription", "Duplicate.optionDTO.shortDescriptionLength");
        }

    }
}
