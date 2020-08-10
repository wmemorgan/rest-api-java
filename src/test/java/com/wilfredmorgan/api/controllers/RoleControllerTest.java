package com.wilfredmorgan.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilfredmorgan.api.ApiApplication;
import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.services.RoleService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private List<Role> roleList = new ArrayList<>();

    public List<Role> displayTestDbRecords(List<Role> list) {

        for (Role u: list) {
            System.out.println(u.getRoleid() + " " + u.getName());
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

        // Add role objects to list
        roleList.add(r1);
        roleList.add(r2);

        // Uncomment when validating database manipulation
//        System.out.println("\n*** BEFORE ***");
//        displayTestDbRecords(roleList);
//        System.out.println();
    }

    @After
    public void tearDown() throws Exception {
        // Uncomment when validating database manipulation
//        System.out.println("\n*** AFTER ***");
//        displayTestDbRecords(roleList);
//        System.out.println();
    }

    @Test
    public void getAllRoles() throws Exception {

        String apiUrl = "/roles/roles";
        Mockito.when(roleService.findAll())
                .thenReturn(roleList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(roleList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns list", er, tr);
    }

    @Test
    public void getRoleById() throws Exception {

        String apiUrl = "/roles/role/2";
        Mockito.when(roleService.findRoleById(2))
                .thenReturn(roleList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(roleList.get(1));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns object", er, tr);

    }

    @Test
    public void getRoleByIdNotFound() throws Exception {
        String apiUrl = "/roles/role/5000";
        Mockito.when(roleService.findRoleById(5000))
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
    public void getRoleByName() throws Exception {

        String apiUrl = "/roles/role/name/admin";
        Mockito.when(roleService.findRoleByName("admin"))
                .thenReturn(roleList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(roleList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API returns object", er, tr);
    }

    @Test
    public void addNewRole() throws Exception {

        String apiUrl = "/roles/role";

        Role role = new Role("data");
        role.setRoleid(3);

        ObjectMapper mapper = new ObjectMapper();
        String roleString = mapper.writeValueAsString(role);

        Mockito.when(roleService.save(any(Role.class)))
                .thenReturn(role);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(roleString);

        mockMvc.perform(rb)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateRole() throws Exception {

        String apiUrl = "/roles/role/{roleid}";

        String newRolename = "Administrator";
        Role role = new Role(newRolename);
        role.setRoleid(1);

        ObjectMapper mapper = new ObjectMapper();
        String roleString = mapper.writeValueAsString(role);

        Mockito.when(roleService.save(any(Role.class)))
                .thenReturn(role);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(roleString);

        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteRoleById() throws Exception {

        String apiUrl = "/roles/role/{roleid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}