package ecare.controllers;

import ecare.model.dto.*;
import ecare.services.api.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class CheckTariffPageControllerTest {

    @Mock
    private OptionService optionServiceImpl;

    @Mock
    private TariffService tariffService;

    @InjectMocks
    private CheckTariffPageController checkTariffPageController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(checkTariffPageController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getCheckTariffPageTest() throws Exception {
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("name");
        List<OptionDTO> setOfAllActiveOptions = new ArrayList<>();
        setOfAllActiveOptions.add(optionDTO);
        when(optionServiceImpl.getActiveOptionDTOs()).thenReturn(setOfAllActiveOptions);

        mockMvc.perform(get("/checkTariff/{tariffName}", "Standart"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkTariffPage"));
    }


    @Test
    public void getAvailableOptionsTest() throws Exception {
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("name");
        Set<OptionDTO> optionsSet = new HashSet<>();
        optionsSet.add(optionDTO);

        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setSetOfOptions(optionsSet);
        when(tariffService.getTariffDTOByTariffNameOrNull(any())).thenReturn(tariffDTO);

        MvcResult result = mockMvc.perform(get("/checkTariff/getAvailableOptions/{oldName}", "Standart")
                .contentType("application/json")).andExpect(status().isOk()).andReturn();
        assertEquals("[\"name\"]", result.getResponse().getContentAsString());
    }

    @Test
    public void submitEditedTariffTest() throws Exception {
        mockMvc.perform(post("/checkTariff/{tariffName}", "Standart")
                .contentType("application/json")).andExpect(status().isOk());
        verify(tariffService, atLeastOnce()).submitValuesFromController(any(), any(),
                any(), any());
    }

    @Test
    public void deleteOption() throws Exception {
        when(tariffService.getTariffDTOByTariffNameOrNull("Standart")).thenReturn(new TariffDTO());
        mockMvc.perform(get("/checkTariff/deleteTariff/{tariffName}", "Standart")
                .contentType("application/json")).andExpect(status().isOk());
        verify(tariffService, atLeastOnce()).convertToEntityAndUpdate(any());
    }


}
