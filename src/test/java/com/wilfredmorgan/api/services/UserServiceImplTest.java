package com.wilfredmorgan.api.services;

import com.wilfredmorgan.api.ApiApplication;
import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.models.User;
import com.wilfredmorgan.api.models.UserRoles;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.wilfredmorgan.api.exceptions.ResourceNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    // Method to validate database manipulation
    public List<User> displayTestDbRecords() {
        List<User> list = userService.findAll();

        for (User u: list) {
            System.out.println(u.getUserid() + " " + u.getUsername() + " " + u.getPrimaryemail());
        }

        return list;
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Uncomment when validating database manipulation
//        System.out.println("\n*** BEFORE ***");
//        displayTestDbRecords();
//        System.out.println();
    }

    @After
    public void tearDown() throws Exception {
        // Uncomment when validating database manipulation
//        System.out.println("\n*** AFTER ***");
//        displayTestDbRecords();
//        System.out.println();
    }

    @Test
    public void A_findAll() {

        List<User> testList = userService.findAll();
        System.out.println("Expect: 4");
        System.out.println("Actual: " + testList.size());

        assertEquals(4, testList.size());
    }

    @Test
    public void B_findById() {
        User testUser = userService.findById(3);
        System.out.println("Expect: Steve");
        System.out.println("Actual: " + testUser.getFirstname());

        assertEquals("Steve", testUser.getFirstname());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void C_notFindById() {
        assertEquals(873, userService.findById(873).getUserid());
    }

    @Test
    public void D_findByUsername() {
        User testUser = userService.findByUsername("hpotter");
        System.out.println("Expect: hpotter");
        System.out.println("Actual: " + testUser.getUsername());

        assertEquals("hpotter", testUser.getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void E_notFindByUsername() {
        assertEquals("sconnors", userService.findByUsername("sconnors"));
    }

    @Test
    public void F_findByEmail() {
        User testUser = userService.findByEmail("george@mail.com");
        System.out.println("Expect: george@mail.com");
        System.out.println("Actual: " + testUser.getPrimaryemail());

        assertEquals("george@mail.com", testUser.getPrimaryemail());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void G_notFindByEmail() {
        assertEquals("gkaplan@mail.com", userService.findByEmail("gkaplan@mail.com").getPrimaryemail());
    }

    @Test
    public void H_findUsernamesContaining() {
        List<User> testList = userService.findUsernamesContaining("Potter");
        System.out.println("Expect: 1");
        System.out.println("Actual: " + testList.size());
        System.out.println("Expect: hpotter");
        System.out.println("Actual: " + testList.get(0).getUsername());

        assertEquals(1, testList.size());
        assertEquals("hpotter", testList.get(0).getUsername());
    }

    @Test
    public void I_findAllByLastName() {
        List<User> testList = userService.findAllByLastName("Rogers");
        System.out.println("Expect: 1");
        System.out.println("Actual: " + testList.size());
        System.out.println("Expect: Rogers");
        System.out.println("Actual: " + testList.get(0).getLastname());

        assertEquals(1, testList.size());
        assertEquals("Rogers", testList.get(0).getLastname());
    }

    @Transactional
    @Test
    public void J_save() {
        Role r = roleService.findRoleByName("user");
        User u = new User("dprince",
                "Diana", "Prince",
                "diana@example.com",
                "mypassword");
        u.getRoles().add(new UserRoles(u, r));

        User addUser = userService.save(u);

        System.out.println("Expect: dprince");
        System.out.println("Actual: " + addUser.getUsername());

        assertNotNull(addUser);
        assertEquals("dprince", addUser.getUsername());
    }

    @Transactional
    @WithUserDetails("admin")
    @Test
    public void K_update() {

        User u = userService.findByUsername("hpotter");
        u.setFirstname("Harrold");

        User updateUser = userService.update(u, 3);

        System.out.println("Expect: Harrold");
        System.out.println("Actual: " + updateUser.getFirstname());

        assertNotNull(updateUser);
        assertEquals("Harrold", updateUser.getFirstname());
    }

    @Transactional
    @Test
    public void L_delete() {
        userService.delete(3);

        List<User> testList = userService.findAll();

        System.out.println("Expect: 3");
        System.out.println("Actual: " + testList.size());

        assertEquals(3, testList.size());

    }

    @Transactional
    @Test
    public void M_deleteAll() {
        userService.deleteAll();
        List<User> testList = userService.findAll();

        System.out.println("Expect: 0");
        System.out.println("Actual: " + testList.size());

        assertEquals(0, testList.size());
    }
}