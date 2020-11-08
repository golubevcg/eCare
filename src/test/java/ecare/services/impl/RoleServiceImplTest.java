package ecare.services.impl;

import ecare.dao.api.RoleDao;
import ecare.model.converters.RoleMapper;
import ecare.model.dto.RoleDTO;
import ecare.model.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @Mock
    RoleDao roleDaoImpl;

    @Mock
    RoleMapper roleMapper;

    @InjectMocks
    RoleServiceImpl roleService;

    private Role role = new Role();

    @Test
    public void saveTest() {
        roleService.save(role);
        verify(roleDaoImpl, atLeastOnce()).save(role);
    }

    @Test
    public void updateTest() {
        roleService.update(role);
        verify(roleDaoImpl, atLeastOnce()).update(role);
    }

    @Test
    public void deleteTest() {
        roleService.delete(role);
        verify(roleDaoImpl, atLeastOnce()).delete(role);
    }

    @Test
    public void getRoleByRoleNameTest(){
        roleService.getRoleByRoleName("roleName");
        verify(roleDaoImpl, atLeastOnce()).getRoleByRoleName("roleName");
    }

    @Test
    public void saveAndConvertToEntityTest(){
        roleService.saveAndConvertToEntity(new RoleDTO());
        verify(roleDaoImpl, atLeastOnce()).save(any());
        verify(roleMapper, atLeastOnce()).toEntity(any());
    }

    @Test
    public void getRoleDTOByRolenameTest(){
        roleService.getRoleByRoleName("roleName");
        verify(roleDaoImpl, atLeastOnce()).getRoleByRoleName(any());
    }

    @Test
    public void convertDTOtoEntity(){
        roleService.convertDTOtoEntity(new RoleDTO());
        verify(roleMapper,atLeastOnce()).toEntity(any());
    }
}
