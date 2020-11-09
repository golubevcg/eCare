package ecare.services.impl;

import ecare.dao.api.UserDao;
import ecare.model.converters.UserMapper;
import ecare.model.dto.*;
import ecare.model.entity.User;
import ecare.services.api.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDaoImpl;

    private final UserMapper userMapper;

    private final TariffService tariffServiceImpl;

    private final OptionService optionServiceImpl;

    private final RoleService roleServiceImpl;

    private final ContractService contractServiceImpl;

    public UserServiceImpl(UserDao userDaoImpl, UserMapper userMapper,
                           TariffService tariffServiceImpl, OptionService optionServiceImpl,
                           RoleService roleServiceImpl, @Lazy ContractService contractServiceImpl) {
        this.userDaoImpl = userDaoImpl;
        this.userMapper = userMapper;
        this.tariffServiceImpl = tariffServiceImpl;
        this.optionServiceImpl = optionServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
        this.contractServiceImpl = contractServiceImpl;
    }

    @Override
    @Transactional
    public void save(User user){
        try{
            userDaoImpl.save(user);
            log.info("User with login=" + user.getLogin() + " was successfully saved!");
        }catch(Exception e){
            log.info("There was an error during saving user with login=" + user.getLogin());
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
        }
    }

    @Override
    @Transactional
    public void convertToEntityAndUpdate(UserDTO userDTO){
        userDaoImpl.update(userMapper.toEntity(userDTO));
    }

    @Override
    @Transactional
    public void delete(User user) {
        try{
            userDaoImpl.delete(user);
            log.info("User with login=" + user.getLogin() + " was successfully deleted!");
        }catch(Exception e){
            log.info("There was an error during deleting user with login=" + user.getLogin());
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
        List<User> listUsers = userDaoImpl.getUserByLogin(login);
        if(!listUsers.isEmpty()){
            User user = listUsers.get(0);
            return  userMapper.toDTO(user);
        }else{
            return null;
        }
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
        log.info("User with login=" + userDTO.getLogin() + " was successfully converted and saved!");

    }

    @Override
    public void submitUserOnControllerData(UserContractDTO userForm, String roleCheckbox,
                                           String selectedTariff, String[] selectedOptionsArray){

        UserDTO userDTO = userForm.getUserDTO();
        RoleDTO roleDTO = new RoleDTO();

        ContractDTO contractDTO = null;

        if( roleCheckbox!=null){
            roleDTO = roleServiceImpl.getRoleDTOByRolename("ADMIN");
        }else{
            roleDTO = roleServiceImpl.getRoleDTOByRolename("USER");
            contractDTO = userForm.getContractDTO();

            if(selectedOptionsArray!=null) {
                for (int i = 0; i < (selectedOptionsArray.length); i++) {
                    contractDTO.addOption(optionServiceImpl.getOptionDTOByNameOrNull(selectedOptionsArray[i]));
                }
            }

            TariffDTO tariffDTO = tariffServiceImpl.getTariffDTOByTariffNameOrNull(selectedTariff);
            if (tariffDTO != null) {
                contractDTO.setTariff(tariffDTO);
            }
            contractDTO.setUser(userDTO);
        }

        HashSet<RoleDTO> roleDTOHashSet = new HashSet<>();
        roleDTOHashSet.add(roleDTO);
        roleDTO.addUser(userDTO);

        userDTO.addContractDTO(contractDTO);
        userDTO.setRoles(roleDTOHashSet);
        userDaoImpl.save( userMapper.toEntity(userDTO) );

        if( roleCheckbox==null) {
            contractDTO.setUser( userMapper.toDTO(userDaoImpl.getUserByLogin(userDTO.getLogin()).get(0)) );
            contractServiceImpl.convertToEntityAndSave(contractDTO);
        }

        log.info("User with login: " + userDTO.getLogin() + " was registered successfully.");
    }

}
