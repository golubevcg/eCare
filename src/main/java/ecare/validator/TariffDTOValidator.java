package ecare.validator;

import ecare.model.dto.TariffDTO;
import ecare.model.entity.User;
import ecare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TariffDTOValidator implements Validator {

    final
    TariffService tariffServiceImpl;

    public TariffDTOValidator(TariffService tariffServiceImpl) {
        this.tariffServiceImpl = tariffServiceImpl;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    private String requiredString = "Required";

    @Override
    public void validate(Object o, Errors errors) {
        TariffDTO tariffDTO = (TariffDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", requiredString);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", requiredString);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortDiscription", requiredString);

        if(tariffServiceImpl.getTariffDTOByTariffNameOrNull( tariffDTO.getName() )!=null){
            errors.rejectValue("name", "Duplicate.tariffDTO.name");
        }

        if(tariffDTO.getShortDiscription().length()<8){
            errors.rejectValue("shortDiscription", "Duplicate.tariffDTO.shortDescriptionLength");
        }

    }
}
