package ecare.services.impl;

import ecare.dao.api.TariffDao;
import ecare.model.converters.TariffMapper;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.model.entity.Contract;
import ecare.model.entity.Tariff;
import ecare.mq.MessageSender;
import ecare.services.api.ContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    public void getTariffDTOByTariffNameOrNullTest(){
        assertNull(tariffService.getTariffDTOByTariffNameOrNull("name"));
        verify(tariffDaoImpl, atLeastOnce()).getTariffByTariffName("name");

        List<Tariff> tariffList = new ArrayList<>();
        Tariff tariff = new Tariff();
        tariff.setName("nameee");
        tariffList.add(tariff);

        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setName("nameee");

        when(tariffDaoImpl.getTariffByTariffName("name")).thenReturn(tariffList);
        when(tariffMapper.toDTO(tariff)).thenReturn(tariffDTO);
        tariffService.getTariffDTOByTariffNameOrNull("name");
        assertNotNull(tariffService.getTariffDTOByTariffNameOrNull("name"));
        verify(tariffDaoImpl, atLeastOnce()).getTariffByTariffName("name");
    }

    @Test
    public void convertDtoToEntityTest(){
        tariffService.convertDtoToEntity(new TariffDTO());
        verify(tariffMapper, atLeastOnce()).toEntity(any());
    }

    @Test
    public void convertToEntityAndSaveTest(){
        tariffService.convertToEntityAndSave(new TariffDTO());
        verify(tariffMapper, atLeastOnce()).toEntity(any());
        verify(tariffDaoImpl, atLeastOnce()).save(any());
    }

    @Test
    public void getActiveTariffs(){
        tariffService.getActiveTariffs();
        verify(tariffDaoImpl, atLeastOnce()).getActiveTariffs();
    }

    @Test
    public void searchFOrTariffDTOByNameTest(){
        tariffService.searchForTariffDTOByName("name");
        verify(tariffDaoImpl, atLeastOnce()).searchForTariffByName("name");
    }

    @Test
    public void submitValuesFromControllerTest(){
        String blockConnectedContracts = "true";
        TariffDTO tariffDTO = new TariffDTO();
        String tariffNameBeforeEditing = "Unlimited calls";
        Set<OptionDTO> availableOptions = new HashSet<>();


        List<Tariff> tariffList = new ArrayList<>();
        Tariff tariff = new Tariff();
        tariff.setName("name");
        tariff.setPrice(1);
        tariff.setShortDiscription("shortDescription");

        Contract contract = new Contract();
        Set<Contract> contractSet = new HashSet<>();
        contractSet.add(contract);

        tariff.setSetOfContracts(contractSet);
        tariffList.add(tariff);
        when(tariffDaoImpl.getTariffByTariffName(any())).thenReturn(tariffList);

        TariffDTO tariffDTO1 = new TariffDTO();
        tariffDTO1.setName("name");
        tariffDTO1.setPrice(1);
        tariffDTO1.setShortDiscription("shortDescription");
        when(tariffMapper.toDTO(tariff)).thenReturn(tariffDTO1);
        tariffService.submitValuesFromController(blockConnectedContracts,tariffDTO,
                                                    tariffNameBeforeEditing,availableOptions);
    }




}
