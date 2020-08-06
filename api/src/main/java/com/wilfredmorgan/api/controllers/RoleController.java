package com.wilfredmorgan.api.controllers;

import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The entry point for clients to access role data
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    /**
     * Using the Role service to process role data
     */
    @Autowired
    RoleService roleService;

    /**
     * List of all roles
     * <br>Example: <a href="http://localhost:2019/roles/roles">http://localhost:2019/roles/roles</a>
     *
     * @return JSON List of all the roles and their associated users
     * @see RoleService#findAll() RoleService.findAll()
     */
    @GetMapping(value = "/roles", produces = {"application/json"})
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.findAll();

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * The Role referenced by the given primary key
     * <br>Example: <a href="http://localhost:2019/roles/role/2">http://localhost:2019/roles/role/2</a>
     *
     * @param roleid The primary key (long) of the role you seek
     * @return JSON object of the role you seek
     * @see RoleService#findRoleById(long) RoleService.findRoleById(long)
     */
    @GetMapping(value = "/role/{roleid}", produces = {"application/json"})
    public ResponseEntity<?> getRoleById(@PathVariable Long roleid) {
        Role r = roleService.findRoleById(roleid);

        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    /**
     * The Role with the given name
     * <br>Example: <a href="http://localhost:2019/roles/role/name/data">http://localhost:2019/roles/role/name/data</a>
     *
     * @param rolename The name of the role you seek
     * @return JSON object of the role you seek
     * @see RoleService#findRoleByName(String) RoleService.findRoleByName(String)
     */
    @GetMapping(value = "/role/name/{rolename}", produces = {"application/json"})
    public ResponseEntity<?> getRoleByName(@PathVariable String rolename) {
        Role r = roleService.findRoleByName(rolename);

        return new ResponseEntity<>(r, HttpStatus.OK);
    }


}
