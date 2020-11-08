package ecare.services.impl;

import ecare.dao.api.OptionDao;
import ecare.model.converters.OptionMapper;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.entity.Option;
import ecare.mq.MessageSender;
import ecare.services.api.ContractService;
import ecare.services.api.TariffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OptionServiceImplTest {

    @Mock
    private OptionDao optionDaoImpl;

    @Mock
    private OptionMapper optionMapper;

    @Mock
    private MessageSender messageSender;

    @Mock
    private ContractService contractServiceImpl;

    @Mock
    private TariffService tariffServiceImpl;

    @InjectMocks
    private OptionServiceImpl optionService;

    @Test
    public void saveTest(){
        optionService.save(new Option());
        verify(optionDaoImpl, atLeastOnce()).save(any());
    }

    @Test
    public void updateTest(){
        optionService.update(new Option());
        verify(optionDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void deleteTest(){
        optionService.delete(new Option());
        verify(optionDaoImpl, atLeastOnce()).delete(any());
    }

    @Test
    public void getOptionByNameTest(){
        optionService.getOptionByName("name");
        verify(optionDaoImpl, atLeastOnce()).getOptionByName("name");
    }

    @Test
    public void searchForOptionByNameTest(){
        ArrayList<Option> optionsArrayList = new ArrayList<>();
        Option option = new Option();
        optionsArrayList.add(option);
        when(optionDaoImpl.searchForOptionByName("name")).thenReturn(optionsArrayList);
        optionService.searchForOptionByName("name");
        verify(optionDaoImpl, atLeastOnce()).searchForOptionByName("name");
        verify(optionMapper, atLeastOnce()).toDTO(any());
    }

    @Test
    public void getActiveOptionDTOsTest(){
        ArrayList<Option> optionsArrayList = new ArrayList<>();
        Option option = new Option();
        optionsArrayList.add(option);
        when(optionDaoImpl.getActiveOptions()).thenReturn(optionsArrayList);
        optionService.getActiveOptionDTOs();
        verify(optionDaoImpl, atLeastOnce()).getActiveOptions();
        verify(optionMapper, atLeastOnce()).toDTO(any());
    }

    @Test
    public void getOptionDTOByNameOrNullTest(){
        String optionName = "TestOption1";
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("name");
        optionDTO.setShortDescription("shd");
        when(optionMapper.toDTO(any())).thenReturn(optionDTO);

        when(optionDaoImpl.getOptionByName(optionName)).thenReturn(new ArrayList<>());
        assertNull( optionService.getOptionDTOByNameOrNull(optionName) );

        ArrayList<Option> optionsArrayList = new ArrayList<>();
        Option option = new Option();
        option.setName("name");
        optionsArrayList.add(option);

        when(optionDaoImpl.getOptionByName(optionName)).thenReturn(optionsArrayList);
        assertEquals("shd", optionService.getOptionDTOByNameOrNull(optionName).getShortDescription());
    }

    @Test
    public void getOptionDTByIdTest(){
        Long optionId = 1l;

        ArrayList<Option> optionsArrayList = new ArrayList<>();
        Option option = new Option();
        option.setName("name");
        optionsArrayList.add(option);
        when(optionDaoImpl.getOptionById(optionId)).thenReturn(optionsArrayList);

        optionService.getOptionDTOById(optionId);
        verify(optionDaoImpl, atLeastOnce()).getOptionById(optionId);
        verify(optionMapper, atLeastOnce()).toDTO(any());
    }

    @Test
    public void getOptionByIdTest(){
        Long optionId = 1l;
        optionService.getOptionById(optionId);
        verify(optionDaoImpl, atLeastOnce()).getOptionById(optionId);
    }

    @Test
    public void convertToEntityAndUpdateTest(){
        optionService.convertToEntityAndUpdate(new OptionDTO());
        verify(optionMapper, atLeastOnce()).toEntity(any());
        verify(optionDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void convertToEntityTest(){
        optionService.convertDTOtoEntity(new OptionDTO());
        verify(optionMapper, atLeastOnce()).toEntity(any());
    }

    @Test
    public void convertToEntityAndSaveTest(){
        optionService.convertToEntityAndSave(new OptionDTO());
        verify(optionDaoImpl, atLeastOnce()).save(any());
        verify(optionMapper, atLeastOnce()).toEntity(any());
    }

    @Test
    public void submitValuesFromControllerTest(){
        String optionName = "Unlimited calls";
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName(optionName);
        optionDTO.setPrice(1);
        optionDTO.setConnectionCost(1);
        optionDTO.setShortDescription("shd");

        ContractDTO contractDTO = new ContractDTO();
        Set<ContractDTO> contractDTOS = new HashSet<>();
        contractDTOS.add(contractDTO);
        optionDTO.setContractsOptions(contractDTOS);

        Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
        OptionDTO optionDTO2 = new OptionDTO();
        optionDTO.setName("optionDTO2Name");
        obligatoryOptionsSet.add(optionDTO2);

        Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
        OptionDTO optionDTO3 = new OptionDTO();
        optionDTO.setName("optionDTO3Name");
        incompatibleOptionsSet.add(optionDTO3);

        String blockConnectedContracts = "true";

        ArrayList<Option> optionsArrayList = new ArrayList<>();
        Option option = new Option();
        option.setName("name");
        optionsArrayList.add(option);
        when(optionDaoImpl.getOptionByName(optionName)).thenReturn(optionsArrayList);

        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setName("name");
        when(optionMapper.toDTO(option)).thenReturn(optionDTO1);

        assertTrue( optionService.submitValuesFromController(optionName, optionDTO,
                                        obligatoryOptionsSet, incompatibleOptionsSet,
                                        blockConnectedContracts) );

        assertTrue( optionService.submitValuesFromController(optionName, optionDTO,
                obligatoryOptionsSet, null,
                blockConnectedContracts) );

        assertTrue( optionService.submitValuesFromController(optionName, optionDTO,
                null, incompatibleOptionsSet,
                blockConnectedContracts) );

    }

//    @Test
//    public void getDependingOptionsJsonTest(){
//        String oldName = "Unlimited calls";
//
//        String str = optionService.getDependedOptionsJson(oldName);
//    }


}
