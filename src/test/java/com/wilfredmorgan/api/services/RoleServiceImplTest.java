package com.wilfredmorgan.api.services;

import com.wilfredmorgan.api.ApiApplication;
import com.wilfredmorgan.api.models.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.wilfredmorgan.api.exceptions.ResourceNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceImplTest {

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

        List<Role> testList = roleService.findAll();
        System.out.println("Expect: 2");
        System.out.println("Actual: " + testList.size());

        assertEquals(2, testList.size());
    }

    @Test
    public void B_findRoleById() {
        Role testRole = roleService.findRoleById(1);
        System.out.println("Expect: ADMIN");
        System.out.println("Actual: " + testRole.getName().toUpperCase());

        assertEquals("ADMIN", testRole.getName().toUpperCase());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void C_notFindRoleById() {
        assertEquals(954, roleService.findRoleById(954).getRoleid());
    }

    @Test
    public void D_findRoleByName() {
        Role testRole = roleService.findRoleByName("user");
        System.out.println("Expect: USER");
        System.out.println("Actual: " + testRole.getName());

        assertEquals("USER", testRole.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void E_notFindRoleByName() {
        assertEquals("DATA", roleService.findRoleByName("data"));
    }

    @Transactional
    @Test
    public void F_save() {
        Role r = new Role("DATA");

        Role addRole = roleService.save(r);

        System.out.println("Expect: DATA");
        System.out.println("Actual: " + addRole.getName());

        assertNotNull(addRole);
        assertEquals("DATA", addRole.getName());
    }

    @Transactional
    @Test
    public void G_update() {
        Role r = roleService.findRoleByName("admin");
        r.setName("Administrator");

        Role updateRole = roleService.update(r, 1);

        System.out.println("Expect: Administrator");
        System.out.println("Actual: " + updateRole.getName());

        assertNotNull(updateRole);
        assertEquals("Administrator", updateRole.getName());
    }

    @Transactional
    @Test
    public void H_delete() {
        roleService.delete(2);

        List<Role> testList = roleService.findAll();

        System.out.println("Expect: 1");
        System.out.println("Actual: " + testList.size());

        assertEquals(1, testList.size());
    }

    @Transactional
    @Test
    public void I_deleteAll() {

        roleService.deleteAll();
        List<Role> testList = roleService.findAll();

        System.out.println("Expect: 0");
        System.out.println("Actual: " + testList.size());

        assertEquals(0, testList.size());
    }
}