package eCare.model.converters;

import eCare.model.dto.UserDTO;
import eCare.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserDTO userDTO){
        return Objects.isNull(userDTO) ? null : modelMapper.map(userDTO, User.class);
    }

    public UserDTO toDTO(User user){
        return Objects.isNull(user) ? null : modelMapper.map(user, UserDTO.class);
    }



}
