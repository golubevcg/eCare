package eCare.model.converters;

import eCare.model.dto.RoleDTO;
import eCare.model.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Role toEntity(RoleDTO roleDTO){
        return Objects.isNull(roleDTO) ? null : modelMapper.map(roleDTO, Role.class);
    }

    public RoleDTO toDTO(Role role){
        return Objects.isNull(role) ? null : modelMapper.map(role, RoleDTO.class);
    }
}
