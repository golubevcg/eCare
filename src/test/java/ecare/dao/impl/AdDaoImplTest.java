package ecare.dao.impl;

import ecare.model.entity.Ad;
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
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private AdDaoImpl adDaoImpl;

    private Ad ad = new Ad();

    private Session session = mock(Session.class);

    @Before
    public void before(){
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void saveTest(){
        adDaoImpl.save(ad);
        verify(session, atLeastOnce()).persist(ad);
    }

    @Test
    public void updateTest(){
        adDaoImpl.update(ad);
        verify(session, atLeastOnce()).update(ad);
    }

    @Test
    public void deleteTest(){
        adDaoImpl.delete(ad);
        verify(session, atLeastOnce()).update(ad);
        assertFalse(ad.isActive());
    }

    @Test
    public void getAdByNameOrNullTest(){

        Query query = mock(Query.class);
        when(session.createQuery(any(), any())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<Ad>());
        adDaoImpl.getAdByNameOrNull("name");

        List<Ad> adList = new ArrayList<>();
        adList.add(ad);
        when(query.list()).thenReturn(adList);
        assertNotNull( adDaoImpl.getAdByNameOrNull("name") );
    }

}
