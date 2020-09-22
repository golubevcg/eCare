package eCare.dao.impl;

import eCare.HibernateSessionFactoryUtil;
import eCare.dao.api.RoleDao;
import eCare.model.enitity.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RoleDaoImpl implements RoleDao {

    @Override
    public void save(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

            Set<Role> allRolesSet = new HashSet<>(session.createQuery("select r from Role r", Role.class)
                    .getResultList());

            if (allRolesSet.isEmpty()) {
                session.save(role);
            }else {
                List<Role> found = session.createQuery("select r from Role r where r.rolename = :roleName", Role.class)
                        .setParameter("roleName", role.getRolename()).getResultList();
                if (found.isEmpty()) {
                    session.save(role);
                }
            }
            transaction.commit();
        } finally {
            session.close();
        }

    }

    @Override
    public void update(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(role);
        transaction2.commit();
        session.close();
    }

    @Override
    public void delete(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.delete(role);
        transaction2.commit();
        session.close();
    }

    @Override
    public List<Role> getRoleByRoleName(String roleName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Role> listOfRoles = session.createQuery(
                "select r " +
                        "from Role r " +
                        "where r.rolename = :name", Role.class)
                .setParameter("name", roleName).list();

        transaction.commit();
        session.close();

        if(listOfRoles.isEmpty()){
            listOfRoles.add(new Role(roleName));
        }

        return listOfRoles;
    }
}
