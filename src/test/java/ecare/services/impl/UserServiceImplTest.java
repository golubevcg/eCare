package ecare.services.impl;

import ecare.dao.api.UserDao;
import ecare.model.converters.UserMapper;
import ecare.model.dto.*;
import ecare.model.entity.User;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import ecare.services.api.RoleService;
import ecare.services.api.TariffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDaoImpl;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TariffService tariffServiceImpl;

    @Mock
    private OptionService optionServiceImpl;

    @Mock
    private RoleService roleServiceImpl;

    @Mock
    private ContractService contractServiceImpl;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveTest(){
        userService.save(new User());
        verify(userDaoImpl, atLeastOnce()).save(any());
    }

    @Test
    public void updateTest(){
        userService.update(new User());
        verify(userDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void convertToEntityAndUpdateTest(){
        userService.convertToEntityAndUpdate(new UserDTO());
        verify(userMapper, atLeastOnce()).toEntity(any());
        verify(userDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void deleteTest(){
        userService.delete(new User());
        verify(userDaoImpl, atLeastOnce()).delete(any());
    }

    @Test
    public void getUserByLoginTest(){
        userService.getUserByLogin("login");
        verify(userDaoImpl, atLeastOnce()).getUserByLogin(any());
    }

    @Test
    public void getUserByLoginDTOorNullTest(){
        userService.getUserDTOByLoginOrNull("login");
        verify(userDaoImpl, atLeastOnce()).getUserByLogin("login");
    }

    @Test
    public void getUserDTOByPassportInfoTest(){
        userService.getUserDTOByPassportInfo("111111");
        verify(userDaoImpl, atLeastOnce()).getUserDTOByPassportInfo("111111");
    }

    @Test
    public void searchUserByLoginTest(){
        userService.searchForUserByLogin("login");
        verify(userDaoImpl, atLeastOnce()).searchForUserByLogin("login");
    }

    @Test
    public void getUserByEmailTest(){
        userService.getUserByEmail("email");
        verify(userDaoImpl, atLeastOnce()).getUserByEmail("email");
    }

    @Test
    public void getUserDTOByEmailTest(){
        userService.getUserDTOByEmail("email");
        verify(userDaoImpl, atLeastOnce()).getUserByEmail("email");
    }

    @Test
    public void convertDTOtoEntityTest(){
        userService.convertDTOtoEntity(new UserDTO());
        verify(userMapper, atLeastOnce()).toEntity(any());
    }

    @Test
    public void convertToEntityAndSave(){
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("login");
        userService.convertToEntityAndSave(new UserDTO());
        verify(userDaoImpl, atLeastOnce()).save(any());
        verify(userMapper,atLeastOnce()).toEntity(any());
    }

    @Test
    public void submitUserOnControllerData(){
        UserContractDTO userForm = new UserContractDTO();
        UserDTO userDTO = new UserDTO();
        userForm.setUser(userDTO);
        String roleCheckbox = "true";
        String selectedTariff = "Standart";
        String[] selectedOptionsArray = {"TestOption1","TestOption2", "TestOption3"};

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRolename("ADMIN");

        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setRolename("USER");
        Set<RoleDTO> roleDTOSet = new HashSet<>();
        roleDTOSet.add(roleDTO1);
        userDTO.setRoles(roleDTOSet);

        Set<UserDTO> userDTOSet = new HashSet<>();
        roleDTO1.setUser(userDTOSet);
        roleDTO1.addUser(userDTO);

        List<User> userSet1 = new ArrayList<>();
        userSet1.add(new User());

        when(roleServiceImpl.getRoleDTOByRolename("USER")).thenReturn(roleDTO1);

        when(optionServiceImpl.getOptionDTOByNameOrNull("TestOption1")).thenReturn(new OptionDTO());
        when(optionServiceImpl.getOptionDTOByNameOrNull("TestOption2")).thenReturn(new OptionDTO());
        when(optionServiceImpl.getOptionDTOByNameOrNull("TestOption3")).thenReturn(new OptionDTO());
        when(tariffServiceImpl.getTariffDTOByTariffNameOrNull(any())).thenReturn(new TariffDTO());
        when(userDaoImpl.getUserByLogin(any())).thenReturn(userSet1);
        userService.submitUserOnControllerData(userForm,null,selectedTariff,selectedOptionsArray);
    }


}
