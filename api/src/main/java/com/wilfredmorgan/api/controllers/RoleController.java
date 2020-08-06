package com.wilfredmorgan.api.controllers;

import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<?> getRoleById(@PathVariable long roleid) {
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

    /**
     * Given a complete Role object, create a new Role record
     * <br>Example: <a href="http://localhost:2019/roles/role">http://localhost:2019/roles/role</a>
     *
     * @param newRole A complete new Role object
     * @return A location header with the URI to the newly created role and a status of CREATED
     * @see RoleService#save(Role) RoleService.save(Role)
     */
    @PostMapping(value = "/role", consumes = {"application/json"})
    public ResponseEntity<?> addNewRole(@Valid @RequestBody Role newRole) {
        newRole.setRoleid(0);
        newRole = roleService.save(newRole);

        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{roleid}")
                .buildAndExpand(newRole.getRoleid())
                .toUri();
        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /**
     * The process allows you to update a role name only!
     * <br>Example: <a href="http://localhost:2019/roles/role/1">http://localhost:2019/roles/role/1</a>
     *
     * @param roleid  The primary key (long) of the role you wish to update
     * @param updateRole The new name (String) for the role
     * @return Status of OK
     * @see RoleService#update(Role, long) RoleService.update(Role, long)
     */
    @PutMapping(value = "/role/{roleid}", consumes = {"application/json"})
    public ResponseEntity<?> updateRole(@Valid @RequestBody Role updateRole,
                                        @PathVariable long roleid) {
        updateRole = roleService.update(updateRole, roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given role
     * <br>Example: <a href="http://localhost:2019/roles/role/3">http://localhost:2019/roles/role/3</a>
     *
     * @param roleid the primary key of the role you wish to delete
     * @return Status of OK
     * @see RoleService#delete(long) RoleService.delete(long)
     */
    @DeleteMapping(value = "/role/{roleid}", produces = {"application/json"})
    public ResponseEntity<?> deleteRoleById(@PathVariable long roleid) {
        roleService.delete(roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
