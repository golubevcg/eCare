package eCare.validator;

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
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "Requried");
        if(user.getLogin().length()<6){
            errors.rejectValue("login", "Size.userForm.login");
        }

        if(userServiceImpl.getUserByLogin(user.getLogin()).size()>0){
            errors.rejectValue("login", "Dublicate.userForm.login");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if(user.getPassword().length()<6){
            errors.rejectValue("password", "Size.userForm.password");
        }

        if(!user.getConfirmPassword().equals(user.getPassword())){
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "Requried");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passportInfo", "Requried");
        if(user.getPassportInfo().toString().length()<10){
            errors.rejectValue("passportInfo", "Size.userForm.passportInfo");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Requried");
        if(user.getAddress().length()<8){
            errors.rejectValue("address", "Size.userForm.address");
        }

    }
}
