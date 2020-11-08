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
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Test
    public void getDependingOptionsJsonTest(){
        String oldName = "Unlimited calls";

        String optionName = "TestOption1";
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("name");
        optionDTO.setShortDescription("shd");
        ArrayList<Option> optionsArrayList = new ArrayList<>();
        optionsArrayList.add(new Option());

        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setName("name1");
        optionDTO1.setShortDescription("shd1");

        OptionDTO optionDTO2 = new OptionDTO();
        optionDTO2.setName("name2");
        optionDTO2.setShortDescription("shd2");

        Set<OptionDTO> optionsSet = new HashSet<>();
        optionsSet.add(optionDTO1);
        optionsSet.add(optionDTO2);

        optionDTO.setIncompatibleOptionsSet(optionsSet);
        optionDTO.setObligatoryOptionsSet(optionsSet);
        when(optionMapper.toDTO(any())).thenReturn(optionDTO);
        when(optionDaoImpl.getOptionByName(oldName)).thenReturn(optionsArrayList);

        String str = optionService.getDependedOptionsJson(oldName);
        assertEquals("[[\"name2\",\"name1\"],[\"name2\",\"name1\"]]", str);
    }

    @Test
    public void checkIncOptionDependenciesToPreventImpossibleDependencyTest(){
        String expJson = "{\"lastSelectedVal\":\"Unlimited calls\"," +
                "\"selectedObligOptions\":[\"Unlimited calls worldwide!\"]}";
        AtomicBoolean foundedErrorDependency = new AtomicBoolean(true);

        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("name");
        optionDTO.setShortDescription("shd");
        ArrayList<Option> optionsArrayList = new ArrayList<>();
        optionsArrayList.add(new Option());

        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setName("name1");
        optionDTO1.setShortDescription("shd1");

        OptionDTO optionDTO2 = new OptionDTO();
        optionDTO2.setName("name2");
        optionDTO2.setShortDescription("shd2");

        Set<OptionDTO> optionsSet = new HashSet<>();
        optionsSet.add(optionDTO1);
        optionsSet.add(optionDTO2);

        optionDTO.setIncompatibleOptionsSet(optionsSet);
        optionDTO.setObligatoryOptionsSet(optionsSet);
        when(optionMapper.toDTO(any())).thenReturn(optionDTO);
        when(optionDaoImpl.getOptionByName(any())).thenReturn(optionsArrayList);

        String str = optionService
                .checkIncOptionDependenciesToPreventImpossibleDependency(expJson, foundedErrorDependency);
        assertEquals("", str);

        String expJson1 = "{\"lastSelectedVal\":\"Unlimited calls\"," +
                "\"selectedObligOptions\":[]}";
        String str1 = optionService
                .checkIncOptionDependenciesToPreventImpossibleDependency(expJson, foundedErrorDependency);
        assertEquals("", str1);
    }

    @Test
    public void returnAllObligatoryOptionsTest(){
        Set<OptionDTO> allObligatoryOptionsSet = new HashSet<>();
        OptionDTO optionDTO = new OptionDTO();

        optionService.returnAllObligatoryOptions(allObligatoryOptionsSet, optionDTO);
    }

    @Test
    public void updateTariffsConnectedToThisOptionsTest(){
        OptionDTO optionDTO = new OptionDTO();

        optionService.updateTariffsConnectedToThisOptions(optionDTO);
    }

    @Test
    public void recursivlyCheckInObligDependeciesTest(){
        OptionDTO lastSelectedOptionDTO = new OptionDTO();
        lastSelectedOptionDTO.setName("lastSelectedName");

        OptionDTO selectedObligOptionDTO = new OptionDTO();
        selectedObligOptionDTO.setName("selectedObligName");
        Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
        obligatoryOptionsSet.add(lastSelectedOptionDTO);
        selectedObligOptionDTO.setObligatoryOptionsSet(obligatoryOptionsSet);
        AtomicBoolean foundedErrorDependency = new AtomicBoolean();

        assertFalse(optionService.recursivlyCheckInObligDependecies
                (lastSelectedOptionDTO, selectedObligOptionDTO, foundedErrorDependency));
        assertTrue(foundedErrorDependency.get());

        Set<OptionDTO> obligatoryOptionsSet1 = new HashSet<>();
        OptionDTO newObligOptionDTO = new OptionDTO();
        newObligOptionDTO.setName("newObligOptionDTO");
        obligatoryOptionsSet1.add(newObligOptionDTO);
        selectedObligOptionDTO.setObligatoryOptionsSet(obligatoryOptionsSet1);
        assertTrue(optionService.recursivlyCheckInObligDependecies
                (lastSelectedOptionDTO,selectedObligOptionDTO,foundedErrorDependency));
    }

    @Test
    public void recursivlyCheckInIncDependeciesTest(){
        OptionDTO lastSelectedOptionDTO = new OptionDTO();
        lastSelectedOptionDTO.setName("lastSelectedName");

        OptionDTO selectedObligOptionDTO = new OptionDTO();
        selectedObligOptionDTO.setName("selectedObligName");
        Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
        incompatibleOptionsSet.add(lastSelectedOptionDTO);
        selectedObligOptionDTO.setIncompatibleOptionsSet(incompatibleOptionsSet);
        AtomicBoolean foundedErrorDependency = new AtomicBoolean();

        assertFalse(optionService.recursivlyCheckInIncDependecies
                (lastSelectedOptionDTO, selectedObligOptionDTO, foundedErrorDependency));
        assertTrue(foundedErrorDependency.get());

        Set<OptionDTO> incompatibleOptionsSet1 = new HashSet<>();
        OptionDTO newObligOptionDTO = new OptionDTO();
        newObligOptionDTO.setName("newObligOptionDTO");
        incompatibleOptionsSet1.add(newObligOptionDTO);
        selectedObligOptionDTO.setIncompatibleOptionsSet(incompatibleOptionsSet1);
        assertTrue(optionService.recursivlyCheckInIncDependecies
                (lastSelectedOptionDTO,selectedObligOptionDTO,foundedErrorDependency));
    }

    @Test
    public void checkIncOptionDependenciesToPreventRecursionTest(){
        String expJson = "{\"currentlyCheckedOptionId\":\"9\"," +
                "\"selectedIncOptions\":[\"200 min\",\"100 Min\",\"300 Min\",\"100 messages\"]}";

        Option option = new Option();
        List<Option> options = new ArrayList<>();
        options.add(option);
        when(optionDaoImpl.getOptionById(any())).thenReturn(options);

        OptionDTO optionDTO = new OptionDTO();
        when(optionMapper.toDTO(option)).thenReturn(optionDTO);
        assertEquals("", optionService.checkIncOptionDependenciesToPreventRecursion(expJson));

        String expJson1 = "{\"currentlyCheckedOptionId\":\"9\"," +
                "\"selectedIncOptions\":[]}";
        assertEquals("", optionService.checkIncOptionDependenciesToPreventRecursion(expJson1));
    }

    @Test
    public void getParentObligatoryOptionDTOsTest(){
        Long optionId = 1l;
        optionService.getParentObligatoryOptionDTOs(optionId);
        verify(optionDaoImpl, atLeastOnce()).getParentObligatoryOptions(any());
    }

    @Test
    public void checkOblOptionDependenciesToPreventRecursion(){
        String expJson = "{\"currentlyCheckedOptionId\":\"9\"," +
                "\"selectedOblOptions\":[\"200 min\",\"100 Min\",\"300 Min\",\"100 messages\"]}";

        Option option = new Option();
        List<Option> options = new ArrayList<>();
        options.add(option);
        when(optionDaoImpl.getOptionById(any())).thenReturn(options);

        OptionDTO optionDTO = new OptionDTO();
        when(optionMapper.toDTO(option)).thenReturn(optionDTO);
        assertEquals("", optionService.checkOblOptionDependenciesToPreventRecursion(expJson));

        String expJson1 = "{\"currentlyCheckedOptionId\":\"9\"," +
                "\"selectedOblOptions\":[]}";
        assertEquals("", optionService.checkOblOptionDependenciesToPreventRecursion(expJson1));
    }

    @Test
    public void getParentIncompatibleOptionDTOs(){
        Long optionId = 1l;
        optionService.getParentIncompatibleOptionDTOs(optionId);
        verify(optionDaoImpl, atLeastOnce()).getParentIncompatibleOptions(any());
    }

    @Test
    public void getAllParentDependencies(){
        Long optionId = 1l;
        optionService.getAllParentDependencies(optionId);
        verify(optionDaoImpl, atLeastOnce()).getAllParentDependencies(any());
    }


}
