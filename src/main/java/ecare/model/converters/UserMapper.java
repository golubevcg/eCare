package ecare.model.converters;

import ecare.model.dto.UserDTO;
import ecare.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserDTO userDTO){
        return Objects.isNull(userDTO) ? null : modelMapper.map(userDTO, User.class);
    }

    public UserDTO toDTO(User user){
        return Objects.isNull(user) ? null : modelMapper.map(user, UserDTO.class);
    }



}
