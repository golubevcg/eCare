package ecare.model.converters;

import ecare.model.dto.RoleDTO;
import ecare.model.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RoleMapperTest {
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleMapper roleMapper;

    Role role = new Role();
    RoleDTO roleDTO = new RoleDTO();

    @Test
    public void toDTOTest(){
        roleMapper.toDTO(role);
        verify(modelMapper, atLeastOnce() ).map(role, RoleDTO.class);
    }

    @Test
    public void toEntityTest(){
        roleMapper.toEntity(roleDTO);
        verify(modelMapper, atLeastOnce() ).map(roleDTO, Role.class);
    }
}

