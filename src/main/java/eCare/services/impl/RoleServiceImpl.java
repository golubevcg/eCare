package eCare.services.impl;

import eCare.dao.api.RoleDao;
import eCare.model.dto.RoleDTO;
import eCare.model.entity.Role;
import eCare.model.converters.RoleMapper;
import eCare.services.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDaoImpl;

    @Autowired
    private RoleMapper roleMapper;

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

    @Override
    public void saveAndConvertToEntity(RoleDTO roleDTO) {
        roleDaoImpl.save(roleMapper.toEntity(roleDTO));
    }

    public RoleDTO getRoleDTOByRolename(String rolename){
        Role role = this.getRoleByRoleName(rolename).get(0);
        return roleMapper.toDTO(role);
    }

    public Role convertDTOtoEntity(RoleDTO roleDTO){
        return roleMapper.toEntity(roleDTO);
    }

}
