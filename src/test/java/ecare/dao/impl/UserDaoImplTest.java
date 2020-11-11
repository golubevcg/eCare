package ecare.dao.impl;

import ecare.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserDaoImpl userDaoImpl;

    private User user = new User();

    private Session session = mock(Session.class);

    @Before
    public void before(){
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void saveTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        user.setPassword("1111111");
        userDaoImpl.save(user);
        verify(session, atLeastOnce()).persist(user);
    }

    @Test
    public void updateTest(){
        userDaoImpl.update(user);
        verify(session, atLeastOnce()).update(user);
    }

    @Test
    public void deleteTest(){
        userDaoImpl.delete(user);
        verify(session, atLeastOnce()).update(user);
    }

    @Test
    public void getUserByLoginTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(userDaoImpl.getUserByLogin("1111").isEmpty());
    }

    @Test
    public void getUserDTOByPassportInfoTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(userDaoImpl.getUserDTOByPassportInfo("1111").isEmpty());

    }

    @Test
    public void searchForUserByLoginTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(userDaoImpl.searchForUserByLogin("1111").isEmpty());
    }

    @Test
    public void getUserByEmailTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(userDaoImpl.getUserByEmail("1111").isEmpty());
    }

    @Test
    public void checkUserRolesTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        userDaoImpl.checkUserRoles(new User(), session);
    }
}
