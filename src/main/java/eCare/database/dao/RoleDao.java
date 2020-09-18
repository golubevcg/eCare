package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.Contract;
import eCare.database.entities.Role;
import eCare.database.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleDao {
    public void save(Role role) {

        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        try {
            Transaction transaction = session.beginTransaction();
            Set<Role> roles = new HashSet<>(session.createQuery("select r from Role r", Role.class)
                    .getResultList());

            List<String> roleNames = new ArrayList<String>(session.createQuery("select r.rolename from Role r",
                    String.class).getResultList());
            if (roles.isEmpty()) {
                session.save(role);
                roles.add(role);
            }else {
                for (String roleName : roleNames) {
                    List<Role> found = session.createQuery("select r from Role r where r.rolename = :roleName", Role.class)
                            .setParameter("roleName", role.getRolename()).getResultList();
                    if (found.isEmpty()) {
                        session.persist(role);
                        roles.add(role);
                    } else {
                        roles.addAll(found);
                    }
                }
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void update(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.update(role);
        transaction2.commit();
        session.close();
    }

    public void delete(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.delete(role);
        transaction2.commit();
        session.close();
    }

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

        return listOfRoles;
    }
}
