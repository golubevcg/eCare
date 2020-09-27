package eCare.validator;

import eCare.model.dto.UserContractDTO;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.User;
import eCare.services.impl.ContractServiceImpl;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UserContractDTOValidator implements Validator {

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
        UserContractDTO user = (UserContractDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondname", "Required");


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "Required");
        if( !Objects.isNull(user.getLogin()) & 6 > user.getLogin().length()){
            errors.rejectValue("login", "Size.userForm.login");
        }

        if(userServiceImpl.getUserByLogin(user.getLogin()).size()>0){
            errors.rejectValue("login", "Dublicate.userForm.login");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if(  !Objects.isNull(user.getPassword()) &  6 > user.getPassword().length() ){
            errors.rejectValue("password", "Size.userForm.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "Required");
        if( !Objects.isNull(user.getPassword()) & !user.getConfirmPassword().equals(user.getPassword())){
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "Required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if( !Objects.isNull(user.getConfirmPassword()) &  6 > user.getConfirmPassword().length()){
            errors.rejectValue("passportInfo", "Size.userForm.passportInfo");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
        if( user.getAddress()!=null & 8 > user.getAddress().length()){
            errors.rejectValue("address", "Size.userForm.address");
        }

        if( !Objects.isNull(user.getContractNumber()) & !user.getContractNumber().matches("[+]*([0-9]{11})")){
            errors.rejectValue("contractNumber", "Pattern.contractDTO.contractNumber");
        }

        if(contractServiceImpl.getContractDTOByNumber(user.getContractNumber()).size()>0){
            errors.rejectValue("login", "Duplicate.contractDTO.contractNumber");
        }

    }
}
