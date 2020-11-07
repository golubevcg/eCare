package ecare.validator;

import ecare.model.dto.ContractDTO;
import ecare.model.dto.UserContractDTO;
import ecare.model.dto.UserDTO;
import ecare.model.entity.User;
import ecare.services.api.ContractService;
import ecare.services.api.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserContractDTOValidatorTest {

    @Mock
    UserService userService;

    @Mock
    ContractService contractService;

    @InjectMocks
    UserContractDTOValidator userContractDTOValidator;

    @Mock
    UserContractDTO userContractDTO;

    @Mock
    Errors errors;

    boolean roleCheckbox = true;

    @Before
    public void before(){
        when(userContractDTO.getLogin()).thenReturn("loginn");
        when(userContractDTO.getPassword()).thenReturn("password");
        when(userContractDTO.getConfirmPassword()).thenReturn("password");
        when(userContractDTO.getEmail()).thenReturn("eMail");
        when(userContractDTO.getPassportInfo()).thenReturn("111111111");
        when(userContractDTO.getAddress()).thenReturn("superPuperAdress");
        when(userContractDTO.getContractNumber()).thenReturn("79998887766");

        when(userService.getUserByLogin(any())).thenReturn(new ArrayList<>());
        when(userService.getUserByEmail(any())).thenReturn(new ArrayList<>());
        when(userService.getUserDTOByPassportInfo(any())).thenReturn(new ArrayList<>());
    }

    @Test
    public void emptyFieldsValidate(){
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("firstname", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("secondname", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("login", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("password", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("confirmPassword", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("dateOfBirth", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("email", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("address", "Required", null, null);

    }

    @Test
    public void shortLoginValidate(){
        when(userContractDTO.getLogin()).thenReturn("login");
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("login", "Size.userForm.login");

    }

    @Test
    public void loginLengthValidate(){
        when(userContractDTO.getLogin()).thenReturn("log");
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("login", "Size.userForm.login");
    }

    @Test
    public void duplicatedLoginValidate(){
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        when(userService.getUserByLogin(any())).thenReturn(userList);
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("login", "Duplicate.userForm.login");
    }

    @Test
    public void pwdLengthValidate(){
        when(userContractDTO.getPassword()).thenReturn("log");
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("password", "Size.userForm.password");
    }

    @Test
    public void pdwDoNotMatchWConfirmPwd(){
        when(userContractDTO.getPassword()).thenReturn("log");
        when(userContractDTO.getConfirmPassword()).thenReturn("111");
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("confirmPassword", "Different.userForm.password");
    }

    @Test
    public void duplicatedEmailValidate(){
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        when(userService.getUserByEmail(any())).thenReturn(userList);
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("email", "Duplicate.userForm.email");
    }

    @Test
    public void duplicatedPassportInfoValidate(){
        ArrayList<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO());
        userList.add(new UserDTO());
        when( userService.getUserDTOByPassportInfo(any()) ).thenReturn(userList);
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors, atLeastOnce()).rejectValue("passportInfo", "Duplicate.userForm.passportInfo");
    }

    @Test
    public void passportInfoLengthValidation(){
        when(userContractDTO.getPassportInfo()).thenReturn("1111");
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors,atLeastOnce()).rejectValue("passportInfo", "Size.userForm.passportInfo");
    }

    @Test
    public void addressLengthValidation(){
        when(userContractDTO.getAddress()).thenReturn("1111");
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors,atLeastOnce()).rejectValue("address", "Size.userForm.address");
    }

    @Test
    public void phoneNumberValidation(){
        when(userContractDTO.getContractNumber()).thenReturn("1111");
        userContractDTOValidator.validate(userContractDTO, errors, roleCheckbox);
        verify(errors,atLeastOnce()).rejectValue("contractNumber", "Pattern.contractDTO.contractNumber");
    }

    @Test
    public void duplicateContractNumber(){
        Boolean rolecheck =null;
        ArrayList<ContractDTO> contractsList = new ArrayList<>();
        contractsList.add(new ContractDTO());
        contractsList.add(new ContractDTO());
        when( contractService.getContractDTOByNumber(any()) ).thenReturn(contractsList);
        userContractDTOValidator.validate(userContractDTO, errors, rolecheck);
        verify(errors, atLeastOnce()).rejectValue("login", "Duplicate.contractDTO.contractNumber");
    }

}
