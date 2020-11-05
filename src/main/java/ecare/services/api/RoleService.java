package ecare.services.api;

import ecare.model.dto.RoleDTO;
import ecare.model.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {
    void save(Role role);
    void update(Role role);
    void delete(Role role);
    List<Role> getRoleByRoleName(String rolename);
    void saveAndConvertToEntity(RoleDTO roleDTO);

    @Transactional
    RoleDTO getRoleDTOByRolename(String rolename);
}
