package com.wilfredmorgan.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilfredmorgan.api.ApiApplication;
import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.models.User;
import com.wilfredmorgan.api.models.UserRoles;
import com.wilfredmorgan.api.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList = new ArrayList<>();

    // Method to validate test list manipulation
    public List<User> displayTestDbRecords(List<User> list) {

        for (User u: list) {
            System.out.println(u.getUserid() + " " + u.getUsername() + " " + u.getPrimaryemail());
        }

        return list;
    }

    @Before
    public void setUp() throws Exception {

        /** // TODO - Investigate feasibility of creating a separate method
         * Create mock database
         */
        // Create Role objects
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        // Set role ids
        r1.setRoleid(1);
        r2.setRoleid(2);

        // Create User objects
        User u1 = new User("admin","Admin","User","admin@mail.com","mypassword");
        User u2 = new User("gkaplan","George","Kaplan","george@mail.com","mypassword");
        User u3 = new User("srogers","Steve","Rogers","srogers@example.com","mypassword");
        User u4 = new User("hpotter", "Harry", "Potter","harry-potter@hogwarts.edu","mypassword");

        // Set user ids
        u1.setUserid(11);
        u2.setUserid(12);
        u3.setUserid(13);
        u4.setUserid(14);

        // Assign roles to users
        u1.getRoles().add(new UserRoles(u1, r1));

        u2.getRoles().add(new UserRoles(u2, r1));
        u2.getRoles().add(new UserRoles(u2, r2));

        u3.getRoles().add(new UserRoles(u3, r2));

        u4.getRoles().add(new UserRoles(u4, r2));

        // Add user objects to list
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);

        // Uncomment when validating database manipulation
//        System.out.println("\n*** BEFORE ***");
//        displayTestDbRecords(userList);
//        System.out.println();
    }

    @After
    public void tearDown() throws Exception {
        // Uncomment when validating database manipulation
//        System.out.println("\n*** AFTER ***");
//        displayTestDbRecords(userList);
//        System.out.println();
    }

    @Test
    public void getAllUsers() throws Exception {

        String apiUrl = "/users/users";
        Mockito.when(userService.findAll())
                .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns list", er, tr);
    }

    @Test
    public void getUserById() throws Exception {

        String apiUrl = "/users/user/13";
        Mockito.when(userService.findById(13))
                .thenReturn(userList.get(2));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(2));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns object", er, tr);
    }

    @Test
    public void getUserByIdNotFound() throws Exception {
        String apiUrl = "/users/user/5000";
        Mockito.when(userService.findById(5000))
                .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns object: ", er, tr);
    }

    @Test
    public void getUserByUsername() throws Exception {
        String apiUrl = "/users/user/username/hpotter";
        Mockito.when(userService.findByUsername("hpotter"))
                .thenReturn(userList.get(2));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(2));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns object", er, tr);
    }

    @Test
    public void getUserByEmail() throws Exception {

        String apiUrl = "/users/user/email/george@mail.com";

        Mockito.when(userService.findByEmail("george@mail.com"))
                .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns object", er, tr);
    }

    @Test
    public void getUsersLikeUsername() throws Exception {

        String apiUrl = "/users/username/like/p";

        List<User> filteredList = userList.stream()
                .filter(u -> u.getUsername().contains("p"))
                .collect(Collectors.toList());

        Mockito.when(userService.findUsernamesContaining("p"))
                .thenReturn(filteredList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(filteredList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns list", er, tr);
    }

    @Test
    public void getAllUsersByLastName() throws Exception {

        String apiUrl = "/users/lastname/like/Rogers";

        List<User> filteredList = userList.stream()
                .filter(u -> u.getLastname().contains("Rogers"))
                .collect(Collectors.toList());

        Mockito.when(userService.findAllByLastName("Rogers"))
                .thenReturn(filteredList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(filteredList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns list", er, tr);
    }

    @Test
    public void addNewUser() throws Exception {

        String apiUrl = "/users/user";

        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User user = new User("dprince",
                "Diana", "Prince",
                "diana@example.com",
                "mypassword");

        user.setUserid(101);
        user.getRoles().add(new UserRoles(user, r2));

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(user);

        Mockito.when(userService.save(any(User.class)))
                .thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void replaceUser() throws Exception {
        String apiUrl = "/users/user/{userid}";

        Role r2 = new Role("user");
        r2.setRoleid(2);

        // create a user
        String newUsername = "captain_america";
        User user = new User(newUsername, "Steve",
                "Rogers",
                "cap@avengers.org",
                "password");
        user.getRoles().add(new UserRoles(user, r2));
        user.setUserid(12);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(user);

        Mockito.when(userService.save(any(User.class)))
                .thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 12)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void updateUser() throws Exception {
        String apiUrl = "/users/user/{userid}";

        User user = userList.get(2);
        user.setFirstname("Harrold");

        Mockito.when(userService.update(user, 13))
                .thenReturn(user);
        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(user);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, user.getUserid())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserById() throws Exception {

        String apiUrl = "/users/user/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "12")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}