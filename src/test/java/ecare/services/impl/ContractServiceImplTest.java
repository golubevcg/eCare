package ecare.services.impl;

import ecare.dao.api.ContractDao;
import ecare.model.converters.ContractMapper;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.model.dto.UserDTO;
import ecare.model.entity.Contract;
import ecare.model.entity.Tariff;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import ecare.services.api.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContractServiceImplTest {



    @Mock
    private ContractDao contractDaoImpl;

    @Mock
    private ContractMapper contractMapper;

    @Mock
    private UserService userServiceImpl;

    @Mock
    private TariffService tariffServiceImpl;

    @Mock
    private OptionService optionServiceImpl;

    @InjectMocks
    private ContractServiceImpl contractService;

    private Contract contract = new Contract();

    @Before
    public void before(){
    }

    @Test
    public void saveTest() {
        contractService.save(contract);
        verify(contractDaoImpl, atLeastOnce()).save(any());
    }

    @Test
    public void updateTest() {
        contractService.update(contract);
        verify(contractDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void deleteTest() {
        contractService.delete(contract);
        verify(contractDaoImpl, atLeastOnce()).delete(any());
    }

    @Test
    public void getContractByNumberTest(){
        List<Contract> returnedContracts = contractService.getContractByNumber("11111111111");
        verify(contractDaoImpl, atLeastOnce()).getContractByNumber("11111111111");
    }

    @Test
    public void getContractByIdTest(){
        List<Contract> returnedContracts = contractService.getContractById(1L);
        verify(contractDaoImpl, atLeastOnce()).getContractById(1L);
    }

    @Test
    public void searchForContractByNumberTest(){
        Contract nContract = new Contract();
        nContract.setContractNumber("11111111111");
        List<Contract> contractsList = new ArrayList<>();
        contractsList.add(nContract);
        when(contractDaoImpl.searchForContractByNumber("11111111111")).thenReturn(contractsList);

        ContractDTO nContractDTO = new ContractDTO();
        nContractDTO.setContractNumber("11111111111");
        when(contractMapper.toDTO(nContract)).thenReturn(nContractDTO);

        List<ContractDTO> returnedContracts = contractService.searchForContractByNumber("11111111111");

        assertEquals(1, returnedContracts.size());
        assertEquals("11111111111", returnedContracts.get(0).getContractNumber());
    }

    @Test
    public void searchForContractByNumberId(){
        Contract nContract = new Contract();
        nContract.setContract_id(1L);
        List<Contract> contractsList = new ArrayList<>();
        contractsList.add(nContract);
        when(contractDaoImpl.getContractById(1L)).thenReturn(contractsList);

        ContractDTO nContractDTO = new ContractDTO();
        nContractDTO.setContract_id(1L);
        when(contractMapper.toDTO(nContract)).thenReturn(nContractDTO);

        List<ContractDTO> returnedContracts = contractService.getContractDTOById(1L);

        assertEquals(1, returnedContracts.size());
        boolean compare = 1L==returnedContracts.get(0).getContract_id();
        assertTrue(compare);
    }

    @Test
    public void getContractDTOByNumberTest(){
        Contract nContract = new Contract();
        nContract.setContractNumber("11111111111");
        List<Contract> contractsList = new ArrayList<>();
        contractsList.add(nContract);
        when(contractDaoImpl.searchForContractByNumber("11111111111")).thenReturn(contractsList);

        ContractDTO nContractDTO = new ContractDTO();
        nContractDTO.setContractNumber("11111111111");
        when(contractMapper.toDTO(nContract)).thenReturn(nContractDTO);

        List<ContractDTO> returnedContracts = contractService.searchForContractByNumber("11111111111");

        assertEquals(1, returnedContracts.size());
        assertEquals("11111111111", returnedContracts.get(0).getContractNumber());
    }

    @Test
    public void getContractDTObyNumberOrNullTest(){
        String number = "111111";
        Contract contract = new Contract();
        contract.setContractNumber(number);

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(number);
        List<Contract> contractsList = new ArrayList<>();
        contractsList.add(contract);

        when(contractDaoImpl.getContractByNumber(number)).thenReturn(contractsList);
        when(contractMapper.toDTO(contract)).thenReturn(contractDTO);

        ContractDTO contractDTO1 = contractService.getContractDTOByNumberOrNull(number);

        verify(contractMapper, atLeastOnce()).toDTO( any() );
        verify(contractDaoImpl, atLeastOnce()).getContractByNumber( any() );

        assertEquals(number, contractDTO1.getContractNumber());
    }

    @Test
    public void getContractDTObyNumberOrNullIsEmptyTest(){
        String number = "111111";
        List<Contract> contractsList = new ArrayList<>();

        when(contractDaoImpl.getContractByNumber(number)).thenReturn(contractsList);
        ContractDTO contractDTO = contractService.getContractDTOByNumberOrNull(number);

        assertEquals(null, contractDTO);
    }

    @Test
    public void convertToEntityAndUpdate(){
        String number = "111111";

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(number);

        when(contractMapper.toEntity(contractDTO)).thenReturn(contract);

        contractService.convertToEntityAndUpdate(contractDTO);
        verify(contractMapper, atLeastOnce()).toEntity(any());
        verify(contractDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void convertDTOtoEntity(){
        ContractDTO contractDTO = new ContractDTO();
        contractService.convertDTOtoEntity(contractDTO);
        verify(contractMapper, atLeastOnce()).toEntity(any());
    }

    @Test
    public void convertToEntityAndSave(){
        String number = "111111";

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(number);

        when(contractMapper.toEntity(contractDTO)).thenReturn(contract);

        contractService.convertToEntityAndSave(contractDTO);
        verify(contractMapper, atLeastOnce()).toEntity(any());
        verify(contractDaoImpl, atLeastOnce()).save(any());
    }

    @Test
    public void submitValuesFromController(){
        String contractNumberBeforeEditing = "72836423413";
        String exportArray = "{\"newNum\":\"72836423413\",\"selectedUserLogin\":\"agolubev\"," +
                "\"selectedTariff\":\"Business\",\"selectedOptions\":[\"Unlimited messages\"," +
                "\"Unlimited internet\"],\"isContractBlocked\":false}";

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("agolubev");

        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setName("Business");

        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("Unlimited messages");
        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setName("Unlimited internet");

        Contract contract = new Contract();
        contract.setContractNumber(contractNumberBeforeEditing);

        List<Contract> contractsList = new ArrayList<>();
        contractsList.add(contract);

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(contractNumberBeforeEditing);

        when(contractDaoImpl.getContractByNumber(contractNumberBeforeEditing)).thenReturn(contractsList);
        when(contractMapper.toDTO(contract)).thenReturn(contractDTO);
        when(userServiceImpl.getUserDTOByLoginOrNull(any())).thenReturn(userDTO);
        when(tariffServiceImpl.getTariffDTOByTariffNameOrNull(any())).thenReturn(tariffDTO);
        when(optionServiceImpl.getOptionDTOByNameOrNull("Unlimited messages")).thenReturn(optionDTO);
        when(optionServiceImpl.getOptionDTOByNameOrNull("Unlimited internet")).thenReturn(optionDTO1);

        contractService.submitValuesFromController(exportArray, contractNumberBeforeEditing);

    }



}
