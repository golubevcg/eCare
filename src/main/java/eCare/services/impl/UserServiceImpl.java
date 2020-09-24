package eCare.services.impl;

import eCare.dao.impl.RoleDaoImpl;
import eCare.dao.impl.UserDaoImpl;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.User;
import eCare.model.converters.UserEntityToUserDTOConverter;
import eCare.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserDaoImpl userDaoImpl;

    @Autowired
    RoleDaoImpl roleDaoImpl;

    @Autowired
    UserEntityToUserDTOConverter userEntityToUserDTOConverter;

    @Override
    public void save(User user){
        userDaoImpl.save(user);
    }

    @Override
    public void update(User user){
        userDaoImpl.update(user);
    }

    @Override
    public void delete(User user) { userDaoImpl.delete(user); }

    @Override
    public List<User> getUserByLogin(String login){
            return userDaoImpl.getUserByLogin(login);
    }

    @Override
    public UserDTO getUserDTOByLogin(String login){
        User user = this.getUserByLogin(login).get(0);
        return  userEntityToUserDTOConverter.convertToDto(user);
    }

    @Override
    public User convertDTOtoEntity(UserDTO userDTO){
        return userEntityToUserDTOConverter.convertDTOtoEntity(userDTO);
    }

    @Override
    public void convertDtoAndSave(UserDTO userDTO){

        userDaoImpl.save(userEntityToUserDTOConverter.convertDTOtoEntity(userDTO));
    }
}
