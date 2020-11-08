package ecare.services.impl;

import ecare.dao.api.RoleDao;
import ecare.model.converters.RoleMapper;
import ecare.model.dto.RoleDTO;
import ecare.model.entity.Role;
import ecare.services.api.RoleService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDaoImpl;

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleDao roleDaoImpl, RoleMapper roleMapper) {
        this.roleDaoImpl = roleDaoImpl;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional
    public void save(Role role) { roleDaoImpl.save(role); }

    @Override
    @Transactional
    public void update(Role role) {
        roleDaoImpl.update(role);
    }

    @Override
    @Transactional
    public void delete(Role role) {
        roleDaoImpl.delete(role);
    }

    @Override
    @Transactional
    public List<Role> getRoleByRoleName(String rolename){
        return roleDaoImpl.getRoleByRoleName(rolename);
    }

    @Override
    @Transactional
    public void saveAndConvertToEntity(RoleDTO roleDTO) {
        roleDaoImpl.save(roleMapper.toEntity(roleDTO));
    }

    @Override
    @Transactional
    public RoleDTO getRoleDTOByRolename(String rolename){
        Role role = roleDaoImpl.getRoleByRoleName(rolename).get(0);
        return roleMapper.toDTO(role);
    }

    public Role convertDTOtoEntity(RoleDTO roleDTO){
        return roleMapper.toEntity(roleDTO);
    }

}
