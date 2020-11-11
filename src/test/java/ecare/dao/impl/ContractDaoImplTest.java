package ecare.dao.impl;

import ecare.model.entity.Contract;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContractDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private ContractDaoImpl contractDao;

    private Contract contract = new Contract();

    private Session session = mock(Session.class);

    @Before
    public void before(){
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void saveTest(){
        contractDao.save(contract);
        verify(session, atLeastOnce()).persist(contract);
    }

    @Test
    public void updateTest(){
        contractDao.update(contract);
        verify(session, atLeastOnce()).update(contract);
    }

    @Test
    public void deleteTest(){
        contractDao.delete(contract);
        verify(session, atLeastOnce()).update(contract);
        assertFalse(contract.isActive());
    }

    @Test
    public void searchContractByNumberTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(contractDao.searchForContractByNumber("1111").isEmpty());
    }

    @Test
    public void getContractByNumberTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(contractDao.getContractByNumber("1111").isEmpty());
    }

    @Test
    public void getContractByIdTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(contractDao.getContractById(1l).isEmpty());
    }

}
