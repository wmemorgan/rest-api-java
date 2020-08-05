package com.wilfredmorgan.api.services;

import com.wilfredmorgan.api.ApiApplication;
import com.wilfredmorgan.api.models.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@Transactional
public class RoleServiceImplTest {

    @Autowired
    RoleService userService;

    @Autowired
    RoleService roleService;

    // Method to validate database manipulation
    public List<Role> displayTestDbRecords() {
        List<Role> list = roleService.findAll();

        for (Role r : list) {
            System.out.println(r.getRoleid() + " " + r.getName());
        }

        return list;
    }

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        // Uncomment when validating database manipulation
//        System.out.println("\n*** BEFORE ***");
//        displayTestDbRecords();

    }

    @After
    public void tearDown() throws Exception {

        // Uncomment when validating database manipulation
//        System.out.println("\n*** AFTER ***");
//        displayTestDbRecords();
    }

    @Test
    public void findAll() {


    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void findRoleById() {
    }

    @Test
    public void findRoleByName() {
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteAll() {
    }
}