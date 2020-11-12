package ecare.controllers;

import ecare.model.dto.UserDTO;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class CheckUserPageControllerTest {

    @Mock
    private UserService userServiceImpl;

    @InjectMocks
    private CheckUserPageController checkUserPageController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(checkUserPageController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getUserRegistrationPageTest() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("login");
        userDTO.setPassportInfo("11111");
        userDTO.setEmail("Emails");

        when(userServiceImpl.getUserDTOByLoginOrNull("login")).thenReturn(userDTO);

        mockMvc.perform(get("/checkUser/{userLogin}", "login"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkUserPage"));
    }

    @Test
    public void checkPassportTest() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("login");
        userDTO.setPassportInfo("11111");
        userDTO.setEmail("Emails");

        List<UserDTO> usersList = new ArrayList<>();
        usersList.add(userDTO);

        when(userServiceImpl.getUserDTOByPassportInfo("login")).thenReturn(usersList);

        MvcResult result = mockMvc.perform(get("/checkUser/checkPassportInfo/{newPassport}", "login"))
                .andExpect(status().isOk()).andReturn();
        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void checkEmailTest() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("login");
        userDTO.setPassportInfo("11111");
        userDTO.setEmail("Emails");

        List<UserDTO> usersList = new ArrayList<>();
        usersList.add(userDTO);

        when(userServiceImpl.getUserDTOByEmail("email")).thenReturn(usersList);

        MvcResult result = mockMvc.perform(get("/checkUser/checkEmail/{newEmail}", "email"))
                .andExpect(status().isOk()).andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }


    @Test
    public void checkLoginTest() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("login");
        userDTO.setPassportInfo("11111");
        userDTO.setEmail("Emails");

        when(userServiceImpl.getUserDTOByLoginOrNull("login")).thenReturn(userDTO);

        MvcResult result = mockMvc.perform(get("/checkUser/checkLogin/{newLogin}", "login"))
                .andExpect(status().isOk()).andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    public void submitValuesTest() throws Exception{
        String json = "{\"firstName\":\"Richard\",\"secondName\":\"Feynman\"," +
                "\"dateOfBirth\":\"1980-05-11\",\"passportInfo\":\"11122233344\"," +
                "\"address\":\"12wewqe111\",\"email\":\"adsasda@ads\",\"login\":\"eegqerg\"}\n";
        when(userServiceImpl.getUserDTOByLoginOrNull(any())).thenReturn(new UserDTO());
        MvcResult result = mockMvc.perform(post("/checkUser/submitChanges/")
                .content(json)
                .contentType("application/json")).andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }


    @Test
    public void deleteUserTest() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("login");
        userDTO.setPassportInfo("11111");
        userDTO.setEmail("Emails");

        when(userServiceImpl.getUserDTOByLoginOrNull("login")).thenReturn(userDTO);

        MvcResult result = mockMvc.perform(get("/checkUser/deleteUser/{login}", "login"))
                .andExpect(status().isOk()).andReturn();
        assertEquals("true", result.getResponse().getContentAsString());

        when(userServiceImpl.getUserDTOByLoginOrNull("login")).thenReturn(null);
        MvcResult result1 = mockMvc.perform(get("/checkUser/deleteUser/{login}", "login"))
                .andExpect(status().isOk()).andReturn();
        assertEquals("false", result1.getResponse().getContentAsString());

    }
}
