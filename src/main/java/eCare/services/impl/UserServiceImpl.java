package eCare.services.impl;

import eCare.dao.impl.RoleDaoImpl;
import eCare.dao.impl.UserDaoImpl;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.User;
import eCare.model.converters.UserMapper;
import eCare.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserDaoImpl userDaoImpl;

    @Autowired
    RoleDaoImpl roleDaoImpl;

    @Autowired
    UserMapper userMapper;

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
        return  userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getUserDTOByPassportInfo(Long passportInfo) {
        return userDaoImpl.getUserDTOByPassportInfo(passportInfo)
                .stream()
                .map(user->userMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public User convertDTOtoEntity(UserDTO userDTO){
        return userMapper.toEntity(userDTO);
    }

    @Override
    public void convertToEntityAndSave(UserDTO userDTO){
        userDaoImpl.save(userMapper.toEntity(userDTO));
    }
}
