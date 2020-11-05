package ecare.model.converters;

import ecare.model.dto.RoleDTO;
import ecare.model.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role toEntity(RoleDTO roleDTO){
        return Objects.isNull(roleDTO) ? null : modelMapper.map(roleDTO, Role.class);
    }

    public RoleDTO toDTO(Role role){
        return Objects.isNull(role) ? null : modelMapper.map(role, RoleDTO.class);
    }
}
