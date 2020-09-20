package eCare.services.impl;

import eCare.dao.impl.RoleDaoImpl;
import eCare.dao.interf.RoleDao;
import eCare.model.Role;
import eCare.services.interf.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDaoImpl roleDaoImpl;

    @Override
    public void save(Role role) { roleDaoImpl.save(role); }

    @Override
    public void update(Role role) {
        roleDaoImpl.update(role);
    }

    @Override
    public void delete(Role role) {
        roleDaoImpl.delete(role);
    }

    @Override
    public List<Role> getRoleByRoleName(String rolename){
        return roleDaoImpl.getRoleByRoleName(rolename);
    }

}
