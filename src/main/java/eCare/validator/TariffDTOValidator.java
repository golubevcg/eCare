package eCare.validator;

import eCare.model.dto.TariffDTO;
import eCare.model.entity.User;
import eCare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TariffDTOValidator implements Validator {

    @Autowired
    TariffService tariffServiceImpl;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TariffDTO tariffDTO = (TariffDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortDiscription", "Required");

        if(tariffServiceImpl.getTariffDTOByTariffnameOrNull( tariffDTO.getName() )!=null){
            errors.rejectValue("name", "Duplicate.tariffDTO.name");
        }

        if(tariffDTO.getShortDiscription().length()<8){
            errors.rejectValue("shortDiscription", "Duplicate.tariffDTO.shortDescriptionLength");
        }

    }
}
