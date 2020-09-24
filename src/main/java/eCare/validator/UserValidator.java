package eCare.validator;

import eCare.model.dto.UserDTO;
import eCare.model.enitity.User;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO user = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "Required");
        if( 6 > user.getLogin().length() && !user.getLogin().isEmpty() ){
            errors.rejectValue("login", "Size.userForm.login");
        }

        if(userServiceImpl.getUserByLogin(user.getLogin()).size()>0){
            errors.rejectValue("login", "Dublicate.userForm.login");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if( 6 > user.getPassword().length() && !user.getPassword().isEmpty() ){
            errors.rejectValue("password", "Size.userForm.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "Required");
        if( !user.getConfirmPassword().equals(user.getPassword()) && user.getConfirmPassword()!=null ){
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "Required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if( 6 > user.getConfirmPassword().length() && !user.getConfirmPassword().isEmpty()){
            errors.rejectValue("passportInfo", "Size.userForm.passportInfo");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
        if( 8 > user.getAddress().length() && !user.getAddress().isEmpty()){
            errors.rejectValue("address", "Size.userForm.address");
        }

    }
}
