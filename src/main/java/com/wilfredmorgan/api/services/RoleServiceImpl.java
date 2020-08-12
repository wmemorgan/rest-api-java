package com.wilfredmorgan.api.services;

import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import com.wilfredmorgan.api.exceptions.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the RoleService interface
 */
@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService{

    /**
     * Connect this service to the Role model
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * Connects to the auditing service to get current user name
     */
    @Autowired
    UserAuditing userAuditing;

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();

        /**
         * findAll returns an iterator set
         * Iterate over the iterator set and add each element to the array list
         */
        roleRepository.findAll()
                .iterator()
                .forEachRemaining(list::add);

        return list;
    }

    @Override
    public Role findRoleById(long id) {

        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role id " + id + " not found"));
    }

    @Override
    public Role findRoleByName(String name) {
        Role r = roleRepository.findByNameIgnoreCase(name);

        if (r == null) {
            throw new ResourceNotFoundException(
                    "Role name " + name + " not found"
            );
        }

        return r;
    }

    @Transactional
    @Override
    public Role save(Role role) {

        Role newRole = new Role();

        if (role.getRoleid() != 0) {
            findRoleById(role.getRoleid());
            newRole.setRoleid(role.getRoleid());
        }

        newRole.setName(role.getName());

        return roleRepository.save(newRole);
    }

    @Transactional
    @Override
    public Role update(Role role, long id) {

        if (role.getName() == null) {
            throw new ResourceNotFoundException("No role name found to update");
        }

        Role r = findRoleById(id);

        roleRepository.updateRoleName(userAuditing
                .getCurrentAuditor()
                .get(), id, role.getName());

        return findRoleById(id);
    }

    @Transactional
    @Override
    public void delete(long id) {
        // Todo - add restriction and exception handling for deleting ADMIN
        findRoleById(id);
        roleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }
}
