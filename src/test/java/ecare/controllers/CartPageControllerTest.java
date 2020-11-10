package ecare.controllers;

import ecare.model.dto.AdDTO;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.mq.MessageSender;
import ecare.services.api.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class CartPageControllerTest {

    @Mock
    ContractService contractService;

    @Mock
    OptionService optionService;

    @Mock
    CartService cartServiceImpl;

    @InjectMocks
    private CartPageController cartPageController;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(cartPageController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getCartPageTest() throws Exception{
        mockMvc.perform(get("/cartPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("cartPage"));
    }

    @Test
    public void removeContractFromSessionTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("cartContractsSetChangedForCart", new HashSet<ContractDTO>());
        when(contractService.getContractDTOByNumberOrNull(any())).thenReturn(new ContractDTO());

        mockMvc.perform(get("/cartPage/removeContractChangingFromSession/{contractNumber}",
                        "+799999").session(session) ).andExpect(status().isOk());
    }

    @Test
    public void removeChangedTariffInContractFromSessionTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("cartContractsSetChangedForCart", new HashSet<ContractDTO>());
        when(contractService.getContractDTOByNumberOrNull(any())).thenReturn(new ContractDTO());

        mockMvc.perform(get("/cartPage/removeTariffChangingFromSession/{contractNumber}",
                "+799999").session(session) ).andExpect(status().isOk());
    }

    @Test
    public void removeIsBlockedInContractFromSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("cartContractsSetChangedForCart", new HashSet<ContractDTO>());
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setBlocked(true);
        when(contractService.getContractDTOByNumberOrNull(any())).thenReturn(contractDTO);
        when(contractService.getContractDTOByNumberOrNull("799999")).thenReturn(contractDTO);

        mockMvc.perform(get("/cartPage/removeIsBlockedInContractFromSession/{contractNumber}",
                "799999").session(session) ).andExpect(status().isOk());
    }

    @Test
    public void removeOptionInContractFromSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("cartContractsSetChangedForCart", new HashSet<ContractDTO>());
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setBlocked(true);
        mockMvc.perform(post("/cartPage/removeOptionInContractFromSession/{contractNumber}", "7888888")
               .contentType("application/json")).andReturn();
        assertTrue(contractDTO.isBlocked());
    }

}
