package ecare.dao.impl;

import ecare.model.entity.Option;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OptionDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private OptionDaoImpl optionDaoImpl;

    private Option option = new Option();

    private Session session = mock(Session.class);

    @Before
    public void before(){
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void saveTest(){
        optionDaoImpl.save(option);
        verify(session, atLeastOnce()).persist(option);
    }

    @Test
    public void updateTest(){
        optionDaoImpl.update(option);
        verify(session, atLeastOnce()).update(option);
    }

    @Test
    public void deleteTest(){
        optionDaoImpl.delete(option);
        verify(session, atLeastOnce()).update(option);
        assertFalse(option.isActive());
    }

    @Test
    public void getOptionByNameTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(optionDaoImpl.getOptionByName("name").isEmpty());
    }

    @Test
    public void getOptionByIdTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(optionDaoImpl.getOptionById(1l).isEmpty());
    }


    @Test
    public void searchForOptionByNameTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(optionDaoImpl.searchForOptionByName("name").isEmpty());

    }

    @Test
    public void getActiveOptionsTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(optionDaoImpl.getActiveOptions().isEmpty());

    }

    @Test
    public void getParentObligatoryOptionsTest(){
        NativeQuery nativeQuery = mock(NativeQuery.class);
        when(session.createSQLQuery(any())).thenReturn(nativeQuery);
        ArrayList<BigInteger> arList = new ArrayList<>();
        BigInteger bigInteger = BigInteger.valueOf(123);
        arList.add(bigInteger);
        assertTrue(optionDaoImpl.getParentObligatoryOptions(1l).isEmpty());

    }

    @Test
    public void getParentIncompatibleOptionsTest(){
        NativeQuery nativeQuery = mock(NativeQuery.class);
        when(session.createSQLQuery(any())).thenReturn(nativeQuery);
        ArrayList<BigInteger> arList = new ArrayList<>();
        BigInteger bigInteger = BigInteger.valueOf(123);
        arList.add(bigInteger);
        assertTrue(optionDaoImpl.getParentIncompatibleOptions(1l).isEmpty());

    }

    @Test
    public void getAllParentDependencies(){
        NativeQuery nativeQuery = mock(NativeQuery.class);
        when(session.createSQLQuery(any())).thenReturn(nativeQuery);
        assertTrue(optionDaoImpl.getAllParentDependencies(1l).isEmpty());
    }


}
