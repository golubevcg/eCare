package eCare.dao.impl;

import eCare.dao.api.RoleDao;
import eCare.model.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Role role) {
        Session session = sessionFactory.getCurrentSession();
        Set<Role> allRolesSet = new HashSet<>(session.createQuery("select r from Role r", Role.class)
                .getResultList());

        if (allRolesSet.isEmpty()) {
            session.persist(role);
        }else {
            List<Role> found = session.createQuery("select r from Role r where r.rolename = :roleName", Role.class)
                    .setParameter("roleName", role.getRolename()).getResultList();
            if (found.isEmpty()) {
                session.persist(role);
            }
        }

    }

    @Override
    public void update(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.update(role);
    }

    @Override
    public void delete(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(role);
    }

    @Override
    public List<Role> getRoleByRoleName(String roleName) {
        Session session = sessionFactory.getCurrentSession();
        List<Role> listOfRoles = session.createQuery(
                "select r " +
                        "from Role r " +
                        "where r.rolename = :name", Role.class)
                .setParameter("name", roleName).list();

        if(listOfRoles.isEmpty()){
            listOfRoles.add(new Role(roleName));
        }

        return listOfRoles;
    }
}
