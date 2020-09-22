package eCare.model.converters;

import eCare.model.dto.RoleDTO;
import eCare.model.enitity.Role;
import eCare.services.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class RoleEntityToRoleDTOConverter {

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    public RoleDTO convertToDto(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRolename(role.getRolename());
        roleDTO.setUser(role.getUser());

        return roleDTO;
    }

    public Role convertDTOtoEntity(RoleDTO roleDTO){
        Role role = roleServiceImpl.getRoleByRoleName(roleDTO.getRolename()).get(0);
        role.setRolename(roleDTO.getRolename());
        role.setUser(roleDTO.getUser());
        return role;
    }
}
