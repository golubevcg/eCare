package ecare.validator;

import ecare.model.dto.OptionDTO;
import ecare.model.entity.User;
import ecare.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OptionDTOValidator implements Validator {

    final
    OptionService optionServiceImpl;

    public OptionDTOValidator(OptionService optionServiceImpl) {
        this.optionServiceImpl = optionServiceImpl;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    private String requiredString = "Required";

    @Override
    public void validate(Object o, Errors errors) {
        OptionDTO option = (OptionDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", requiredString);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", requiredString);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "connectionCost", requiredString);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortDescription", requiredString);

        if(optionServiceImpl.getOptionByName(option.getName()).size()>0){
            errors.rejectValue("name", "Duplicate.optionDTO.name");
        }

        if(option.getShortDescription().length()<8){
            errors.rejectValue("shortDescription", "Duplicate.optionDTO.shortDescriptionLength");
        }

    }
}
