package ecare.controllers;

import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.UserDTO;
import ecare.model.entity.Option;
import ecare.model.entity.Tariff;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import ecare.services.api.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class CheckContractPageControllerTest {


    @Mock
    UserService userServiceImpl;

    @Mock
    TariffService tariffServiceImpl;

    @Mock
    OptionService optionServiceImpl;

    @Mock
    ContractService contractServiceImpl;

    @InjectMocks
    CheckContractPageController checkContractPageController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(checkContractPageController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getCheckContractPageTest() throws Exception {
        Long contractId = 1l;
        ContractDTO contractDTO = new ContractDTO();
        UserDTO userDTO = new UserDTO();
        contractDTO.setUser(userDTO);
        contractDTO.setContractNumber("788899");

        ArrayList<ContractDTO> contractDTOArrayList = new ArrayList<>();
        contractDTOArrayList.add(contractDTO);
        when(contractServiceImpl.getContractDTOById(contractId)).thenReturn(contractDTOArrayList);
        when(tariffServiceImpl.getActiveTariffs()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/checkContract/{contractId}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkContractPage"));
    }

    @Test
    public void returnOptionsListForSelectedTariffTest() throws Exception {
        String tariffName = "UnlimitedCalls";
        Tariff tariff = new Tariff();
        tariff.setName(tariffName);

        Option option = new Option();
        option.setName("optionName");
        Option option1 = new Option();
        option1.setName("optionName1");

        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option);
        optionSet.add(option1);

        tariff.setSetOfOptions(optionSet);
        List<Tariff> tariffList = new ArrayList<>();
        tariffList.add(tariff);

        when(tariffServiceImpl.getTariffByTariffName(tariffName)).thenReturn(tariffList);
        MvcResult result = mockMvc.perform(get("/checkContract/loadOptionByTariff/{selectedTariff}", tariffName)
                .contentType("application/json")).andReturn();
        String str = result.getResponse().getContentAsString();
        assertEquals("[\"optionName1\",\"optionName\"]", str);
    }

    @Test
    public void getUsersListTest() throws Exception {
        String userLogin = "agol";
        UserDTO user = new UserDTO();
        user.setLogin("login");
        UserDTO user1 = new UserDTO();
        user1.setLogin("login1");
        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(user);
        userDTOList.add(user1);

        when(userServiceImpl.searchForUserByLogin(userLogin)).thenReturn(userDTOList);
        MvcResult result = mockMvc.perform(get("/checkContract/getUsersList")
                .param("term", userLogin)
                .contentType("application/json")).andReturn();
        String str = result.getResponse().getContentAsString();
        assertEquals("[\"login\",\"login1\"]", str);
    }

    @Test
    public void getAvailableOptionsTest() throws Exception {
        String oldNumber = "788899988";
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(oldNumber);

        OptionDTO option = new OptionDTO();
        option.setName("optionName");
        OptionDTO option1 = new OptionDTO();
        option1.setName("optionName1");

        Set<OptionDTO> optionSet = new HashSet<>();
        optionSet.add(option);
        optionSet.add(option1);

        contractDTO.setSetOfOptions(optionSet);
        List<ContractDTO> contractDTOList = new ArrayList<>();
        contractDTOList.add(contractDTO);
        when(contractServiceImpl.getContractDTOByNumber(oldNumber)).thenReturn(contractDTOList);

        MvcResult result = mockMvc.perform(get("/checkContract/getAddionalOptions/{oldNumber}", oldNumber)
                .contentType("application/json")).andReturn();
        String str = result.getResponse().getContentAsString();
        assertEquals("[\"optionName1\",\"optionName\"]", str);
    }

    @Test
    public void checkNewNameTest() throws Exception {
        String newNum = "788899988";
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(newNum);
        when(contractServiceImpl.getContractDTOByNumber(newNum)).thenReturn(new ArrayList<>());
        MvcResult result = mockMvc.perform(get("/checkContract/checkNewNumber/{newNum}", newNum)
                .contentType("application/json")).andReturn();
        String str = result.getResponse().getContentAsString();
        assertEquals("false", str);
    }

    @Test
    public void checkUserTest() throws Exception {
        String selectedUser = "agolubev";
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(selectedUser);
        when(userServiceImpl.getUserDTOByLoginOrNull(selectedUser)).thenReturn(userDTO);
        MvcResult result = mockMvc.perform(get("/checkContract/checkUser/{selectedUser}", selectedUser)
                .contentType("application/json")).andReturn();
        String str = result.getResponse().getContentAsString();
        assertEquals("true", str);
    }

    @Test
    public void submitValuesTest() throws Exception {
        mockMvc.perform(post("/checkContract/submitChanges/")
                .content("[\"Standart\",\"TestTariff1_\",\"WorldWide\"]")
                .contentType("application/json")).andReturn();
        verify(contractServiceImpl, atLeastOnce()).submitValuesFromController(any(), any());
    }

    @Test
    public void deleteContractTest() throws Exception {
        String contractNumber = "777888999";

        ContractDTO contractDTO = new ContractDTO();
        when(contractServiceImpl.getContractDTOByNumberOrNull(contractNumber)).thenReturn(contractDTO);
        MvcResult result = mockMvc.perform(get("/checkContract/deleteContract/{contractNumber}", contractNumber)
                .contentType("application/json")).andReturn();
        String str = result.getResponse().getContentAsString();
        assertEquals("true", str);

        when(contractServiceImpl.getContractDTOByNumberOrNull(contractNumber)).thenReturn(null);
        MvcResult result1 = mockMvc.perform(get("/checkContract/deleteContract/{contractNumber}", contractNumber)
                .contentType("application/json")).andReturn();
        String str1 = result1.getResponse().getContentAsString();
        assertEquals("false", str1);
    }

}
