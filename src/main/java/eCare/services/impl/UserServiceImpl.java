package eCare.services.impl;

import eCare.controllers.EntrancePageController;
import eCare.dao.api.RoleDao;
import eCare.dao.api.UserDao;
import eCare.model.converters.UserMapper;
import eCare.model.dto.UserDTO;
import eCare.model.entity.User;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    static final Logger log = Logger.getLogger(EntrancePageController.class);

    private final UserDao userDaoImpl;

    private final UserMapper userMapper;

    public UserServiceImpl(UserDao userDaoImpl, UserMapper userMapper) {
        this.userDaoImpl = userDaoImpl;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void save(User user){
        try{
            userDaoImpl.save(user);
            log.info("User with login=" + user.getLogin() + " was successfully saved!");
        }catch(Exception e){
            log.info("There was an error during saving user with login=" + user.getLogin());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void update(User user){
        try{
            userDaoImpl.update(user);
            log.info("User with login=" + user.getLogin() + " was successfully updated!");
        }catch(Exception e){
            log.info("There was an error during updating user with login=" + user.getLogin());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void convertToEntityAndUpdate(UserDTO userDTO){
        this.update(userMapper.toEntity(userDTO));
    }

    @Override
    @Transactional
    public void delete(User user) {
        try{
            userDaoImpl.delete(user);
            log.info("User with login=" + user.getLogin() + " was successfully deleted!");
        }catch(Exception e){
            log.info("There was an error during deleting user with login=" + user.getLogin());
            e.printStackTrace();
        }
    }

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
        this.save(userMapper.toEntity(userDTO));
    }

}
