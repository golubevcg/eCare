package ecare.dao.impl;

import ecare.dao.api.RoleDao;
import ecare.model.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    private final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Role role) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select r from Role r", Role.class);
        Set<Role> allRolesSet = new HashSet<>(query.getResultList());

        if (allRolesSet.isEmpty()) {
            session.persist(role);
        }else {
            Query query1 = session.createQuery("select r from Role r " +
                    "where r.rolename = :roleName", Role.class);
            query1.setParameter("roleName", role.getRolename());
            List<Role> found = query1.getResultList();
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
        Query query = session.createQuery(
                "select r " +
                        "from Role r " +
                        "where r.rolename = :name", Role.class);
        query.setParameter("name", roleName);
        List<Role> listOfRoles = query.list();

        if(listOfRoles.isEmpty()){
            listOfRoles.add(new Role(roleName));
        }

        return listOfRoles;
    }
}
