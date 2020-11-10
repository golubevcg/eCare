package ecare.dao.impl;

import ecare.model.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private RoleDaoImpl roleDaoImpl;

    private Role role = new Role();

    private Session session = mock(Session.class);

    @Before
    public void before(){
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void saveTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        ArrayList<Role> rolesList = new ArrayList<>();

        when(query.getResultList()).thenReturn(rolesList);
        roleDaoImpl.save(role);
        verify(session, atLeastOnce()).persist(role);
    }

    @Test
    public void updateTest(){
        roleDaoImpl.update(role);
        verify(session, atLeastOnce()).update(role);
    }

    @Test
    public void deleteTest(){
        roleDaoImpl.delete(role);
        verify(session, atLeastOnce()).delete(role);
    }

    @Test
    public void getRoleByRoleName(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        ArrayList<Role> rolesList = new ArrayList<>();
        Role role = new Role();
        rolesList.add(role);
        when(query.list()).thenReturn(rolesList);
        List<Role> roleList = roleDaoImpl.getRoleByRoleName("name");
    }


}
