package com.wilfredmorgan.api.repositories;

import com.wilfredmorgan.api.models.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CRUD repository connecting Role model to the rest of the application
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * Find a role by name using case insensitive search
     *
     * @param name
     * @return Role object by name
     */
    Role findByNameIgnoreCase(String name);

    /**
     * Updates the role name based on the given role id
     *
     * @param uname
     * @param roleid
     * @param name
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE roles SET name = :name, lastmodifiedby = :uname, lastmodifieddate = CURRENT_TIMESTAMP WHERE roleid = :roleid",
            nativeQuery = true)
    void updateRoleName(String uname, long roleid, String name);
}
