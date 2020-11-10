package ecare.model.converters;

import ecare.model.dto.UserDTO;
import ecare.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserMapper userMapper;

    User user = new User();
    UserDTO userDTO = new UserDTO();

    @Test
    public void toDTOTest(){
        userMapper.toDTO(user);
        verify(modelMapper, atLeastOnce() ).map(user, UserDTO.class);
    }

    @Test
    public void toEntityTest(){
        userMapper.toEntity(userDTO);
        verify(modelMapper, atLeastOnce() ).map(userDTO, User.class);
    }
}