package ecare.dao.api;

import ecare.model.entity.Role;

import java.util.List;

public interface RoleDao {
    void save(Role role);
    void update(Role role);
    void delete(Role role);
    List<Role> getRoleByRoleName(String roleName);
}
