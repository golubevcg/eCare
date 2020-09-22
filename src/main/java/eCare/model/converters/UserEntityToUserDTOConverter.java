package eCare.model.converters;

import eCare.model.dto.UserDTO;
import eCare.model.enitity.User;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class UserEntityToUserDTOConverter {

    @Autowired
    private UserServiceImpl userServiceImpl;

    public UserDTO convertToDto(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setSecondname(user.getSecondname());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setPassportInfo(user.getPassportInfo());
        userDTO.setAddress(user.getAddress());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles(user.getRoles());
        userDTO.setListOfContracts(user.getListOfContracts());

        return userDTO;
    }

    public User convertDTOtoEntity(UserDTO userDTO){

        User user = userServiceImpl.getUserByLogin(userDTO.getLogin()).get(0);
        user.setLogin(userDTO.getLogin());
        user.setFirstname(userDTO.getFirstname());
        user.setSecondname(userDTO.getSecondname());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPassportInfo(userDTO.getPassportInfo());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());
        user.setListOfContracts(userDTO.getListOfContracts());

        return user;
    }


}
