package ecare.dao.impl;

import ecare.dao.api.UserDao;
import ecare.model.entity.Role;
import ecare.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        this.checkUserRoles(user, session);
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        session.persist(user);
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void delete(User user){
        Session session = sessionFactory.getCurrentSession();
        user.setActive(false);
        session.update(user);
    }


    @Override
    public List<User> getUserByLogin(String login){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.login = :login", User.class);
        query.setParameter( "login", login );
        return query.list();
    }

    @Override
    public List<User> getUserDTOByPassportInfo(String passportInfo) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.passportInfo = :passportInfo", User.class);
        query.setParameter( "passportInfo", passportInfo );
        return query.list();
    }

    @Override
    public List<User> searchForUserByLogin(String searchInput) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.login like:string", User.class);
        query.setParameter("string", "%" + searchInput + "%");
        return query.list();
    }

    @Override
    public List<User> getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.email = :email", User.class);
        query.setParameter( "email", email );
        return query.list();
    }

    @Override
    public void checkUserRoles(User user, Session session){
        Query query = session.createQuery("select r from Role r", Role.class);
        Set<Role> allRolesSet = new HashSet<>(query.getResultList());
        Set<Role> userRolesCheckedSet = new HashSet<>();

        if(!allRolesSet.isEmpty()){
            for (Role role: user.getRoles()) {
                Query query1 = session.createQuery("select r from Role r where r.rolename = :roleName", Role.class);
                query1.setParameter("roleName", role.getRolename());
                Role foundedRole = (Role)query1.getSingleResult();
                ifFoundedRoleNullThenAssignRoleElseFoundedRole(foundedRole, userRolesCheckedSet, role);
            }
            user.setRoles(userRolesCheckedSet);
        }
    }

    private void ifFoundedRoleNullThenAssignRoleElseFoundedRole(Role foundedRole,
                                                                Set<Role> userRolesCheckedSet,
                                                                Role role){
        if(null==foundedRole){
            userRolesCheckedSet.add(role);
        }else{
            userRolesCheckedSet.add(foundedRole);
        }
    }

}
