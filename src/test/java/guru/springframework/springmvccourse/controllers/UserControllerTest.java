package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.domain.User;
import guru.springframework.springmvccourse.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by yriyMitsiuk on 06.06.2018.
 */
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = Stream.generate(User::new).limit(3).collect(Collectors.toList());

        when(userService.getAll()).thenReturn((List)users);
        mockMvc.perform(get("/user/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", hasSize(3)));
    }

    @Test
    public void testGet() throws Exception {
        Integer id = 7;
        when(userService.get(id)).thenReturn(new User());
        mockMvc.perform(get("/user/view/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/view"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userform"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 7;
        when(userService.get(id)).thenReturn(new User());
        mockMvc.perform(get("/user/edit/"+id))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userform"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 7;
        String username = "Alex";

        User returnedUser = new User();
        returnedUser.setId(id);
        returnedUser.setUsername(username);

        when(userService.saveOrUpdate(ArgumentMatchers.<User>any())).thenReturn(returnedUser);

        mockMvc.perform(post("/user").param("id", "7").param("username", "Alex"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/view/7"))
                .andExpect(model().attribute("user", instanceOf(User.class)))
                .andExpect(model().attribute("user", hasProperty("username", is(username))));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveOrUpdate(userCaptor.capture());
        assertEquals(username, userCaptor.getValue().getUsername());
    }
}