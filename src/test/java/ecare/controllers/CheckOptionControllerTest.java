package ecare.controllers;

import ecare.model.dto.OptionDTO;
import ecare.services.api.OptionService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class CheckOptionControllerTest {

    @Mock
    private OptionService optionServiceImpl;

    @InjectMocks
    private CheckOptionPageController checkOptionController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(checkOptionController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getCheckOptionPage() throws Exception {
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName("name");
        when(optionServiceImpl.getOptionDTOByNameOrNull(any())).thenReturn(optionDTO);

        List<OptionDTO> setOfAllActiveOptions = new ArrayList<>();
        setOfAllActiveOptions.add(optionDTO);
        setOfAllActiveOptions.add(optionDTO);
        when(optionServiceImpl.getActiveOptionDTOs()).thenReturn(setOfAllActiveOptions);

        mockMvc.perform(get("/checkOption/{optionName}", "UnlimitedCalls"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkOptionPage"));
    }

    @Test
    public void setDependingOptionsTest() throws Exception {
        String json = "[[{\"selected\":true,\"disabled\":false,\"text\":\"TestOption5\",\"id\":\"TestOption5\"," +
                "\"title\":\"\",\"_resultId\":\"select2-selectedObligatoryOptions-result-uyje-TestOption5\"," +
                "\"element\":{}}],[{\"selected\":true,\"disabled\":false,\"text\":\"TestOption9_\"," +
                "\"id\":\"TestOption9_\",\"title\":\"\"," +
                "\"_resultId\":\"select2-selectedIncompatibleOptions-result-tpql-TestOption9_\",\"element\":{}}]]";

        when(optionServiceImpl.getOptionDTOByNameOrNull(any())).thenReturn(any());
        MvcResult result = mockMvc.perform(post("/checkOption/submitArraysValues")
                .content(json)
                .contentType("application/json")).andReturn();
        assertTrue(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void submitEditedOptionTest() throws Exception {
        when(optionServiceImpl.submitValuesFromController(any(),any(),any(),any(),any())).thenReturn(true);
        mockMvc.perform(post("/checkOption/{optionName}", "UnlimitedCalls")
                .contentType("application/json")).andExpect(status().isOk());
        verify(optionServiceImpl, atLeastOnce()).submitValuesFromController(any(),any(),any(),any(),any());
    }

    @Test
    public void newNameValidationCheckInDBTest() throws Exception {
        when(optionServiceImpl.getOptionDTOByNameOrNull(any())).thenReturn(any());
        MvcResult result = mockMvc.perform(get("/checkOption/checkNewName/{newName}", "UnlimitedCalls")
                .contentType("application/json")).andExpect(status().isOk()).andReturn();
        assertEquals("false", result.getResponse().getContentAsString());
    }

    @Test
    public void deleteOptionTest() throws Exception {
        when(optionServiceImpl.getOptionDTOByNameOrNull(any())).thenReturn(new OptionDTO());
        MvcResult result = mockMvc.perform(get("/checkOption/deleteOption/{optionName}", "UnlimitedCalls")
                .contentType("application/json")).andExpect(status().isOk()).andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    public void getDependedOptionsTest() throws Exception {
        mockMvc.perform(get("/checkOption/getIncompatibleAndObligatoryOptions/{oldName}",
                        "UnlimitedCalls")
                .contentType("application/json")).andExpect(status().isOk());
        verify(optionServiceImpl, atLeastOnce()).getDependedOptionsJson("UnlimitedCalls");
    }

    @Test
    public void checkIncOptionDependenciesToPreventRecursionTest() throws Exception {
        when(optionServiceImpl.checkIncOptionDependenciesToPreventRecursion("json")).thenReturn(any());

        mockMvc.perform(post("/checkOption/checkIncOptionDependenciesToPreventRecursion/")
                .content("json").contentType("application/json"));
        verify(optionServiceImpl, atLeastOnce()).checkIncOptionDependenciesToPreventRecursion("json");
    }

    @Test
    public void checkOblOptionDependenciesToPreventRecursionTest() throws Exception {
        when(optionServiceImpl.checkOblOptionDependenciesToPreventRecursion("json")).thenReturn(any());

        mockMvc.perform(post("/checkOption/checkOblOptionDependenciesToPreventRecursion/")
                .content("json").contentType("application/json"));
        verify(optionServiceImpl, atLeastOnce()).checkOblOptionDependenciesToPreventRecursion("json");
    }

}
