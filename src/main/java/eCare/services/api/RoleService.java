package eCare.services.api;

import eCare.model.dto.RoleDTO;
import eCare.model.enitity.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);
    void update(Role role);
    void delete(Role role);
    List<Role> getRoleByRoleName(String rolename);
    void saveAndConvertToEntity(RoleDTO roleDTO);
}
