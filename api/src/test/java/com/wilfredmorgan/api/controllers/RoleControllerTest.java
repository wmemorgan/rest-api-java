package com.wilfredmorgan.api.controllers;

import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.services.RoleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RoleController.class)
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
    }

    @Test
    public void getRoleById() throws Exception {
    }

    @Test
    public void getRoleByName() throws Exception {
    }

    @Test
    public void addNewRole() throws Exception {
    }

    @Test
    public void updateRole() throws Exception {
    }

    @Test
    public void deleteRoleById() throws Exception {
    }
}