package com.wilfredmorgan.api.repositories;

import com.wilfredmorgan.api.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * CRUD repository connecting User model to the rest of the application
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find a user by username
     *
     * @param username
     * @return User object with the matching username
     */
    User findByUsernameIgnoreCase(String username);

    /**
     * Find a user by email address
     *
     * @param email
     * @return User object with the matching email address
     */
    User findByPrimaryemailIgnoreCase(String email);

    /**
     * Find all users whose username contains a given case insensitive substring
     *
     * @param username
     * @return List of users whose name contains the given substring
     */
    List<User> findByUsernameContainingIgnoreCase(String username);


    /**
     * Find all users whose last name contains a given case insensitive substring
     *
     * @param lastname
     * @return List of users whose last name contains the given substring
     */
    List<User> findByLastnameContainingIgnoreCase(String lastname);
}
