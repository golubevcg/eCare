package eCare.database.services;

import eCare.database.dao.ContractDao;
import eCare.database.dao.RoleDao;
import eCare.database.entities.Contract;
import eCare.database.entities.Role;

public class RoleService {

    RoleDao roleDao = new RoleDao();

    public void save(Role role) {
        roleDao.save(role);
    }

    public void update(Role role) {
        roleDao.update(role);
    }

    public void delete(Role role) {
        roleDao.delete(role);
    }

}
