package ecare.controllers;

import ecare.model.dto.AdDTO;
import ecare.model.dto.TariffDTO;
import ecare.mq.MessageSender;
import ecare.services.api.AdService;
import ecare.services.api.TariffService;
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
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class AdPageControllerTest {

    @Mock
    MessageSender messageSender;

    @Mock
    TariffService tariffServiceImpl;

    @Mock
    AdService adServiceImpl;

    @InjectMocks
    private AdPageController adPageController;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(adPageController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getAdPageTest() throws Exception{
        when(tariffServiceImpl.getActiveTariffs()).thenReturn(new ArrayList<>());
        AdDTO adDTO = new AdDTO();
        adDTO.setName("main");

        TariffDTO tariffDTO = new TariffDTO();
        Set<TariffDTO> tariffDTOSet = new HashSet<>();
        tariffDTOSet.add(tariffDTO);

        adDTO.setSetOfTariffs(tariffDTOSet);
        when(adServiceImpl.getAdDTOByNameOrNull("main")).thenReturn(adDTO);

        mockMvc.perform(get("/adPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("adPage"));
    }

    @Test
    public void submitAdChangesTest() throws Exception{
        AdDTO adDTO = new AdDTO();
        adDTO.setName("main");

        TariffDTO tariffDTO = new TariffDTO();
        Set<TariffDTO> tariffDTOSet = new HashSet<>();
        tariffDTOSet.add(tariffDTO);

        adDTO.setSetOfTariffs(tariffDTOSet);
        when(adServiceImpl.getAdDTOByNameOrNull("main")).thenReturn(adDTO);
        when(tariffServiceImpl.getTariffDTOByTariffNameOrNull(any())).thenReturn(new TariffDTO());

        MvcResult result = mockMvc.perform(post("/adPage/submit").content("[\"Standart\",\"TestTariff1_\",\"WorldWide\"]")
                .contentType("application/json")).andReturn();
        assertEquals("true", result.getResponse().getContentAsString());

        when(adServiceImpl.getAdDTOByNameOrNull("main")).thenReturn(null);
        MvcResult result1 = mockMvc.perform(post("/adPage/submit").content("[\"Standart\",\"TestTariff1_\",\"WorldWide\"]")
                .contentType("application/json")).andReturn();
        assertEquals("false", result1.getResponse().getContentAsString());
    }

    @Test
    public void getTariffInfoTest() throws Exception {
        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setShortDiscription("shortDescription");
        tariffDTO.setPrice(11);
        String tariffName = "testTariff";
        when(tariffServiceImpl.getTariffDTOByTariffNameOrNull(any())).thenReturn(tariffDTO);

        MvcResult result = mockMvc.perform(get("/adPage/getTariffInfo/{tariffName}", tariffName)
                .contentType("application/json")).andReturn();
        String str = result.getResponse().getContentAsString();
        assertEquals("[\"shortDescription\",\"11\"]", str);
    }

}
