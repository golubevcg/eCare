package eCare.services.impl;

import eCare.dao.api.RoleDao;
import eCare.dao.api.UserDao;
import eCare.model.converters.UserMapper;
import eCare.model.dto.UserDTO;
import eCare.model.entity.User;
import eCare.services.api.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private final UserDao userDaoImpl;

    private final RoleDao roleDaoImpl;

    private final UserMapper userMapper;

    public UserServiceImpl(UserDao userDaoImpl, RoleDao roleDaoImpl, UserMapper userMapper) {
        this.userDaoImpl = userDaoImpl;
        this.roleDaoImpl = roleDaoImpl;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void save(User user){
        userDaoImpl.save(user);
    }

    @Override
    @Transactional
    public void update(User user){
        userDaoImpl.update(user);
    }

    @Override
    @Transactional
    public void convertToEntityAndUpdate(UserDTO userDTO){
        userDaoImpl.update(userMapper.toEntity(userDTO));
    }

    @Override
    @Transactional
    public void delete(User user) { userDaoImpl.delete(user); }

    @Override
    @Transactional
    public List<User> getUserByLogin(String login){
            return userDaoImpl.getUserByLogin(login);
    }

    @Override
    @Transactional
    public UserDTO getUserDTOByLoginOrNull(String login){
        List<User> listUsers = this.getUserByLogin(login);
        if(!listUsers.isEmpty() && listUsers!=null){
            User user = listUsers.get(0);
            return  userMapper.toDTO(user);
        }else{ return null;}
    }

    @Override
    @Transactional
    public List<UserDTO> getUserDTOByPassportInfo(String passportInfo) {
        return userDaoImpl.getUserDTOByPassportInfo(passportInfo)
                .stream()
                .map(user->userMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserDTO> searchForUserByLogin(String searchInput) {
        return userDaoImpl.searchForUserByLogin(searchInput)
                .stream()
                .map(u->userMapper.toDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<User> getUserByEmail(String email) {
        return userDaoImpl.getUserByEmail(email);
    }

    @Override
    @Transactional
    public List<UserDTO> getUserDTOByEmail(String email) {
        return userDaoImpl.getUserByEmail(email)
                .stream()
                .map(u->userMapper.toDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User convertDTOtoEntity(UserDTO userDTO){
        return userMapper.toEntity(userDTO);
    }

    @Override
    @Transactional
    public void convertToEntityAndSave(UserDTO userDTO){
        userDaoImpl.save(userMapper.toEntity(userDTO));
    }
}
