package com.wilfredmorgan.api.controllers;

import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.models.User;
import com.wilfredmorgan.api.models.UserRoles;
import com.wilfredmorgan.api.services.UserService;
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
@WebMvcTest(value = UserController.class)
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
        User u1 = new User("gkaplan","George","Kaplan","george@mail.com","mypassword");
        User u2 = new User("srogers","Steve","Rogers","srogers@example.com","mypassword");
        User u3 = new User("hpotter", "Harry", "Potter","harry-potter@hogwarts.edu","mypassword");

        // Set user ids
        u1.setUserid(11);
        u2.setUserid(12);
        u3.setUserid(13);

        // Assign roles to users
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));

        u2.getRoles().add(new UserRoles(u2, r1));
        u2.getRoles().add(new UserRoles(u2, r2));

        u3.getRoles().add(new UserRoles(u3, r2));

        // Add user objects to list
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);

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
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void getUserByUsername() {
    }

    @Test
    public void getUserByEmail() {
    }

    @Test
    public void getUsersLikeUsername() {
    }

    @Test
    public void getAllUsersByLastName() {
    }

    @Test
    public void addNewUser() {
    }

    @Test
    public void replaceUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUserById() {
    }
}