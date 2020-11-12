package ecare.controllers;

import ecare.model.dto.UserDTO;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class ContractDetailsPageControllerTest {

    @Mock
    private UserService userServiceImpl;

    @Mock
    private TariffService tariffServiceImpl;

    @Mock
    private ContractService contractServiceImpl;

    @Mock
    private OptionService optionServiceImpl;

    @InjectMocks
    private ContractDetailsPageController contractDetailsPageController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(contractDetailsPageController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getContractDetailsPageTest() throws Exception{
        mockMvc.perform(get("/contractDetails/{contractID}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("contractDetailsPage"));
        verify(contractServiceImpl, atLeastOnce()).addContractDetailsToModelForPage(any(),eq("1"), any());
    }

}
