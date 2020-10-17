package eCare.services.impl;

import eCare.dao.api.RoleDao;
import eCare.dao.api.UserDao;
import eCare.model.dto.UserDTO;
import eCare.model.entity.User;
import eCare.model.converters.UserMapper;
import eCare.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDaoImpl;

    @Autowired
    private RoleDao roleDaoImpl;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user){
        userDaoImpl.save(user);
    }

    @Override
    public void update(User user){
        userDaoImpl.update(user);
    }

    @Override
    public void convertToEntityAndUpdate(UserDTO userDTO){
        userDaoImpl.update(userMapper.toEntity(userDTO));
    }

    @Override
    public void delete(User user) { userDaoImpl.delete(user); }

    @Override
    public List<User> getUserByLogin(String login){
            return userDaoImpl.getUserByLogin(login);
    }

    @Override
    public UserDTO getUserDTOByLoginOrNull(String login){
        List<User> listUsers = this.getUserByLogin(login);
        if(!listUsers.isEmpty() && listUsers!=null){
            User user = listUsers.get(0);
            return  userMapper.toDTO(user);
        }else{ return null;}
    }

    @Override
    public List<UserDTO> getUserDTOByPassportInfo(Long passportInfo) {
        return userDaoImpl.getUserDTOByPassportInfo(passportInfo)
                .stream()
                .map(user->userMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> searchForUserByLogin(String searchInput) {
        return userDaoImpl.searchForUserByLogin(searchInput)
                .stream()
                .map(u->userMapper.toDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUserByEmail(String email) {
        return userDaoImpl.getUserByEmail(email);
    }

    @Override
    public List<UserDTO> getUserDTOByEmail(String email) {
        return userDaoImpl.getUserByEmail(email)
                .stream()
                .map(u->userMapper.toDTO(u))
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
