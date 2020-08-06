package com.wilfredmorgan.api.services;

import com.wilfredmorgan.api.models.Role;

import java.util.List;

/**
 * The service that works with the Role model
 */
public interface RoleService {

    /**
     * Returns a list of all the roles
     *
     * @return List of roles
     */
    List<Role> findAll();

    /**
     * Returns the role with the given primary key
     *
     * @param id
     * @return Role object with the given primarry key or throws an exception if not found
     */
    Role findRoleById(long id);

    /**
     * Returns the role with the given name
     *
     * @param name The name (String) of the role you seek
     * @return Role object with given name or throws an exception if not found
     */
    Role findRoleByName(String name);

    /**
     * Given a complete role object, saves that role object in the database
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database
     *
     * @param role The role object (Role) to be saved
     * @return The saved role object including any automatically generated fields
     */
    Role save(Role role);

    /**
     * Updates the provided fields in the role record referenced by the primary key
     *
     * @param role Only the role fields to be updated
     * @param id The primary key (long) of the user to be updated
     * @return Update role object
     */
    Role update(Role role, long id);

    /**
     * Deletes the role record based off the provided primary key
     *
     * @param id The primary key (long) of the role to be deleted
     */
    void delete(long id);

    /**
     * Deletes all records and their associated records from the database
     */
    void deleteAll();
}
