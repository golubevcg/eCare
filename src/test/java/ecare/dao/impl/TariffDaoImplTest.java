package ecare.dao.impl;

import ecare.model.entity.Ad;
import ecare.model.entity.Tariff;
import ecare.mq.MessageSender;
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

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@RunWith(MockitoJUnitRunner.class)
public class TariffDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private  TariffDaoImpl tariffDaoImpl;

    private Tariff tariff = new Tariff();

    private Session session = mock(Session.class);

    @Before
    public void before(){
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void saveTest(){
        tariffDaoImpl.save(tariff);
        verify(session, atLeastOnce()).persist(tariff);
    }

    @Test
    public void updateTest(){
        tariffDaoImpl.update(tariff);
        verify(session, atLeastOnce()).update(tariff);
    }

    @Test
    public void deleteTest(){
        tariffDaoImpl.delete(tariff);
        verify(session, atLeastOnce()).update(tariff);
        assertFalse(tariff.isActive());
    }

    @Test
    public void getTariffByTariffNameTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        tariffDaoImpl.getTariffByTariffName("name");
    }

    @Test
    public void getAllTariffsTest(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        tariffDaoImpl.getAllTariffs();
    }

    @Test
    public void getActiveTariffs(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        tariffDaoImpl.getActiveTariffs();
    }

    @Test
    public void searchForTariffByName(){
        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        tariffDaoImpl.searchForTariffByName("name");
    }

}
