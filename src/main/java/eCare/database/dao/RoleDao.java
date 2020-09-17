package eCare.database.dao;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.entities.Contract;
import eCare.database.entities.Role;
import eCare.database.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoleDao {
    public void save(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction2 = session.beginTransaction();
        session.save(role);
        transaction2.commit();
        session.close();
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
