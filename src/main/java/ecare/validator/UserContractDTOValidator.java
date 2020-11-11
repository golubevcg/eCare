package ecare.validator;

import ecare.model.dto.UserContractDTO;
import ecare.model.entity.User;
import ecare.services.api.ContractService;
import ecare.services.api.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserContractDTOValidator implements Validator {

    final
    UserService userServiceImpl;

    final
    ContractService contractServiceImpl;

    public UserContractDTOValidator(UserService userServiceImpl, ContractService contractServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.contractServiceImpl = contractServiceImpl;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //unused default validate with such constructor
    }

    private String requiredString = "Required";
    private String loginString = "login";


    public void validate(Object o, Errors errors, Boolean roleCheckbox) {

        UserContractDTO user = (UserContractDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", requiredString);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondname", requiredString);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, loginString, requiredString);
        if( 6 > user.getLogin().length()){
            errors.rejectValue(loginString, "Size.userForm.login");
        }

        if(userServiceImpl.getUserByLogin(user.getLogin()).size()>0){
            errors.rejectValue(loginString, "Duplicate.userForm.login");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", requiredString);
        if( 6 > user.getPassword().length() ){
            errors.rejectValue("password", "Size.userForm.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", requiredString);
        if( !user.getConfirmPassword().equals(user.getPassword())){
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", requiredString);


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", requiredString);
        if( !userServiceImpl.getUserByEmail(user.getEmail()).isEmpty()){
            errors.rejectValue("email", "Duplicate.userForm.email");
        }


        if( userServiceImpl.getUserDTOByPassportInfo(user.getPassportInfo()).size()>0){
            errors.rejectValue("passportInfo", "Duplicate.userForm.passportInfo");
        }

        if( user.getPassportInfo().length()<8 ){
            errors.rejectValue("passportInfo", "Size.userForm.passportInfo");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", requiredString);
        if( 8 > user.getAddress().length()){
            errors.rejectValue("address", "Size.userForm.address");
        }

        if(!user.getContractNumber().matches("[+]*([0-9]{11})")){
            errors.rejectValue("contractNumber", "Pattern.contractDTO.contractNumber");
        }

        if( roleCheckbox==null) {
            if(contractServiceImpl.getContractDTOByNumber(user.getContractNumber()).size()>0){
                errors.rejectValue("contractNumber", "Duplicate.contractDTO.contractNumber");
            }
        }

    }
}
