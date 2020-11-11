package ecare.services.impl;

import ecare.dao.api.ContractDao;
import ecare.model.converters.ContractMapper;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.model.dto.UserDTO;
import ecare.model.entity.Contract;
import ecare.model.entity.User;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import ecare.services.api.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
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
    public void searchForContractByNumberIdTest(){
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
    public void convertToEntityAndUpdateTest(){
        String number = "111111";

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(number);

        when(contractMapper.toEntity(contractDTO)).thenReturn(contract);

        contractService.convertToEntityAndUpdate(contractDTO);
        verify(contractMapper, atLeastOnce()).toEntity(any());
        verify(contractDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void convertDTOtoEntityTest(){
        ContractDTO contractDTO = new ContractDTO();
        contractService.convertDTOtoEntity(contractDTO);
        verify(contractMapper, atLeastOnce()).toEntity(any());
    }

    @Test
    public void convertToEntityAndSaveTest(){
        String number = "111111";

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(number);

        when(contractMapper.toEntity(contractDTO)).thenReturn(contract);

        contractService.convertToEntityAndSave(contractDTO);
        verify(contractMapper, atLeastOnce()).toEntity(any());
        verify(contractDaoImpl, atLeastOnce()).save(any());
    }

    @Test
    public void submitValuesFromControllerTest(){
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
        when(contractMapper.toEntity(contractDTO)).thenReturn(any());

        contractService.submitValuesFromController(exportArray, contractNumberBeforeEditing);

        verify(optionServiceImpl, atLeastOnce()).getOptionDTOByNameOrNull(any());
        verify(contractMapper,atLeastOnce()).toEntity(contractDTO);
        verify(contractDaoImpl, atLeastOnce()).update(any());
    }

    @Test
    public void getDependingOptionsTest(){
        HttpSession httpSession = mock(HttpSession.class);
        String selectedOptionId = "15";
        String[] stringArrayInfoFromFront = new String[]{"WorldWide","true"," "," " +
                                                                 "","5","9","13","14","15","17","20","79115462345"};

        long id = Long.parseLong(selectedOptionId);
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setOption_id(id);
        when(optionServiceImpl.getOptionDTOById(any())).thenReturn(new OptionDTO());

        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setName("WorldWide");
        when(tariffServiceImpl.getTariffDTOByTariffNameOrNull(stringArrayInfoFromFront[0])).thenReturn(tariffDTO);

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber("11111");
        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setContractNumber("11111");
        Set<ContractDTO> contractDTOsSet = new HashSet<>();
        contractDTOsSet.add(contractDTO);
        contractDTOsSet.add(contractDTO1);

        when(httpSession.getAttribute(("cartContractsSetChangedForCart"))).thenReturn(contractDTOsSet);

        assertEquals(12, contractService
                .getDependingOptions(httpSession, selectedOptionId, stringArrayInfoFromFront).length());
    }

    @Test
    public void cascadeCheckOptionDependenciesTest(){
        OptionDTO currentOption = new OptionDTO();
        Set<String> incompatibleOptionIds = new HashSet<>();
        Set<String> obligatoryOptionIds = new HashSet<>();
        Set<OptionDTO> notSelectedOptionIds = new HashSet<>();
        Boolean isChecked = true;

        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("name");

        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setName("name1");
        OptionDTO optionDTO2 = new OptionDTO();
        optionDTO2.setName("name2");

        OptionDTO optionDTO3 = new OptionDTO();
        optionDTO3.setName("name3");
        OptionDTO optionDTO4 = new OptionDTO();
        optionDTO4.setName("name4");

        Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
        incompatibleOptionsSet.add(optionDTO1);
        incompatibleOptionsSet.add(optionDTO2);

        Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
        incompatibleOptionsSet.add(optionDTO3);
        incompatibleOptionsSet.add(optionDTO4);

        optionDTO.setIncompatibleOptionsSet(incompatibleOptionsSet);
        optionDTO.setObligatoryOptionsSet(obligatoryOptionsSet);

        contractService.cascadeCheckOptionDependencies(currentOption, incompatibleOptionIds, obligatoryOptionIds,
                                                       notSelectedOptionIds, isChecked);

        boolean isCheckedF = false;
        contractService.cascadeCheckOptionDependencies(currentOption, incompatibleOptionIds, obligatoryOptionIds,
                notSelectedOptionIds, isCheckedF);
        assertTrue(isChecked);
    }

    @Test
    public void getSortedListOfOptionsTest(){
        String contractNumber = "11111";
        String selectedTariffName = "Unlimited calls";
        HttpSession session = mock(HttpSession.class);

        HashSet<ContractDTO> cartContractsSetChangedForCart = new HashSet<>();
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(contractNumber);

        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setContractNumber("22222");

        cartContractsSetChangedForCart.add(contractDTO);
        cartContractsSetChangedForCart.add(contractDTO1);
        when(session.getAttribute(any())).thenReturn(cartContractsSetChangedForCart);

        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setName("name1");
        optionDTO1.setOption_id(1l);
        OptionDTO optionDTO2 = new OptionDTO();
        optionDTO2.setName("name2");
        optionDTO2.setOption_id(2l);

        OptionDTO optionDTO3 = new OptionDTO();
        optionDTO3.setName("name3");
        optionDTO3.setOption_id(3l);
        OptionDTO optionDTO4 = new OptionDTO();
        optionDTO4.setName("name4");
        optionDTO4.setOption_id(4l);

        Set<OptionDTO> setOfOptions = new HashSet<>();
        setOfOptions.add(optionDTO1);
        setOfOptions.add(optionDTO2);
        setOfOptions.add(optionDTO3);
        setOfOptions.add(optionDTO4);

        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setName(selectedTariffName);
        tariffDTO.setSetOfOptions(setOfOptions);
        when(tariffServiceImpl.getTariffDTOByTariffNameOrNull(any())).thenReturn(tariffDTO);

        String str = contractService.getSortedListOfOptions(contractNumber, selectedTariffName, session);
        assertNotNull(str);

        when(tariffServiceImpl.getTariffDTOByTariffNameOrNull(any())).thenReturn(null);
        String str1 = contractService.getSortedListOfOptions(contractNumber, selectedTariffName, session);
        assertEquals("[]", str1);
    }


    @Test
    public void addContractDetailsToModelForPageTest(){
        Model model = mock(Model.class);
        String contractID = "1";
        HttpSession session = mock(HttpSession.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("fN");
        userDTO.setSecondname("sN");

        UserDTO userDTO1 = new UserDTO();
        userDTO.setFirstname("fN1");
        userDTO.setSecondname("sN1");

        HashSet<ContractDTO> cartContractsSetChangedForCart = new HashSet<>();
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber("11111");
        contractDTO.setContract_id(1l);
        contractDTO.setUser(userDTO);
        contractDTO.setActive(true);

        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setName("name");
        tariffDTO.setPrice(11);
        tariffDTO.setShortDiscription("shd");

        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setName("name1");
        optionDTO1.setOption_id(1l);
        OptionDTO optionDTO2 = new OptionDTO();
        optionDTO2.setName("name2");
        optionDTO2.setOption_id(2l);

        OptionDTO optionDTO3 = new OptionDTO();
        optionDTO3.setName("name3");
        optionDTO3.setOption_id(3l);
        OptionDTO optionDTO4 = new OptionDTO();
        optionDTO4.setName("name4");
        optionDTO4.setOption_id(4l);

        Set<OptionDTO> setOfOptions = new HashSet<>();
        setOfOptions.add(optionDTO1);
        setOfOptions.add(optionDTO2);
        setOfOptions.add(optionDTO3);
        setOfOptions.add(optionDTO4);
        tariffDTO.setSetOfOptions(setOfOptions);
        contractDTO.setTariff(tariffDTO);

        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setContractNumber("22222");
        contractDTO1.setContract_id(2l);
        contractDTO1.setUser(userDTO1);

        cartContractsSetChangedForCart.add(contractDTO);
        cartContractsSetChangedForCart.add(contractDTO1);
        when(session.getAttribute(any())).thenReturn(cartContractsSetChangedForCart);

        when(tariffServiceImpl.getActiveTariffs()).thenReturn(new ArrayList<TariffDTO>());
        contractService.addContractDetailsToModelForPage(model,contractID,session);
        assertTrue(contractDTO.isActive());
    }

    @Test
    public void getCurrentContractFromSessionByContractIdTest(){
        HttpSession session = mock(HttpSession.class);
        String contractID = "1";
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber("11111");
        contractDTO.setContract_id(1l);

        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setContractNumber("22222");
        contractDTO1.setContract_id(2l);

        HashSet<ContractDTO> cartContractsSetChangedForCart = new HashSet<>();
        cartContractsSetChangedForCart.add(contractDTO);
        cartContractsSetChangedForCart.add(contractDTO1);
        when(session.getAttribute(any())).thenReturn(cartContractsSetChangedForCart);

        ContractDTO contractDTO2 = contractService.getCurrentContractFromSessionByContractId(session, contractID);
        assertEquals("11111", contractDTO2.getContractNumber());
    }

    @Test
    public void validateContractNumberFromControllerTest(){
        String contractNumber = "11111";
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);

        contractService.validateContractNumberFromController("", bindingResult, model);
        verify(bindingResult, atLeastOnce())
                .addError(new ObjectError("phoneNumberEmptyError", "This field is required."));

        BindingResult bindingResult1 = mock(BindingResult.class);
        contractService.validateContractNumberFromController(contractNumber, bindingResult1, model);
        verify(bindingResult1, atLeastOnce()).addError(new ObjectError("phoneNumberPatterError",
                "Phone number should look like this: +7XXXXXXXXXX."));

        BindingResult bindingResult2 = mock(BindingResult.class);
        contractService.validateContractNumberFromController("+79119998811", bindingResult2, model);
        verify(bindingResult2, never()).addError(any());
    }

    @Test
    public void validateLoginFromControllerTest(){
        String selectedLogin = "selectedLogin";
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);

        User user = new User();
        ArrayList<User> usersArrayList = new ArrayList<>();
        usersArrayList.add(user);

        when(userServiceImpl.getUserByLogin(any())).thenReturn(usersArrayList);
        contractService.validateLoginFromController(selectedLogin,bindingResult, model);
        verify(bindingResult, never()).addError(any());

        BindingResult bindingResult1 = mock(BindingResult.class);
        when(userServiceImpl.getUserByLogin(any())).thenReturn(new ArrayList<User>());
        contractService.validateLoginFromController(selectedLogin,bindingResult1, model);
        verify(bindingResult1, atLeastOnce())
                .addError(new ObjectError("userList",
                        "Please select existing user from drop-down list"));

        BindingResult bindingResult2 = mock(BindingResult.class);
        contractService.validateLoginFromController("",bindingResult2, model);
        verify(bindingResult1, atLeastOnce())
                .addError(new ObjectError("userList",
                        "Please select existing user from drop-down list"));
    }


}
