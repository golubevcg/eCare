package ecare.services.impl;

import ecare.dao.api.TariffDao;
import ecare.model.converters.TariffMapper;
import ecare.model.entity.Tariff;
import ecare.mq.MessageSender;
import ecare.services.api.ContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TariffServiceImplTest {

    @Mock
    private TariffDao tariffDaoImpl;

    @Mock
    private TariffMapper tariffMapper;

    @Mock
    private ContractService contractServiceImpl;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Test
    public void saveTest(){
        tariffService.save(new Tariff());
        verify(tariffDaoImpl, atLeastOnce()).save(any());
    }

    @Test
    public void updateTest(){
        tariffService.update(new Tariff());
        verify(tariffDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void deleteTest(){
        tariffService.delete(new Tariff());
        verify(tariffDaoImpl, atLeastOnce()).delete(any());
    }

    @Test
    public void getTariffByTariffNameTest(){
        tariffService.getTariffByTariffName("name");
        verify(tariffDaoImpl, atLeastOnce()).getTariffByTariffName("name");
    }

    @Test
    public void getAllTariffsTest(){
        tariffService.getAllTariffs();
        verify(tariffDaoImpl, atLeastOnce()).getAllTariffs();
    }



}
