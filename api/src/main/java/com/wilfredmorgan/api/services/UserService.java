package com.wilfredmorgan.api.services;

import com.wilfredmorgan.api.models.User;

import java.util.List;

/**
 * The service that works with the User model
 * <p>
 */
public interface UserService {

    /**
     * Returns a list of all the users
     *
     * @return List of users
     */
    List<User> findAll();

    /**
     * Returns the user with the given primary key
     *
     * @param id The primary key (long) of the user you seek
     * @return User object with the given primary key or throws an exception if not found
     */
    User findById(long id);

    /**
     * Returns the user with the given username
     *
     * @param username The username (String) of the user you seek
     * @return User object with given username or throws an exception if not found
     */
    User findByUsername(String username);

    /**
     * Returns the user with the given email address
     *
     * @param email The email (String) of the user you seek
     * @return User object with the given email or throws an exception if not found
     */
    User findByEmail(String email);

    /**
     * A list of users whose username contains the given substring
     *
     * @param username The substring (String) containing the username
     * @return List of users whose username contains the given substring
     */
    List <User> findUsernamesContaining(String username);

    /**
     * A list of users whose last name contains the given substring
     *
     * @param lastname The substring (String) containing the last name
     * @return List of users whose last name contains the given substring
     */
    List <User> findAllByLastName(String lastname);

    /**
     * Given a complete user object, saves that user object in the database
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database
     *
     * @param user The user object (User) to be saved
     * @return The saved user object including any automatically generated fields
     */
    User save(User user);

    /**
     * Updates the provided fields in the user record referenced by the primary key
     *
     * @param user Only the user fields to be updated
     * @param id The primary key (long) of the user to be updated
     * @return Update user object
     */
    User update(User user, long id);

    /**
     * Deletes the user record based off the provided primary key
     *
     * @param id The primary key (long) of the user to be deleted
     */
    void delete(long id);

    /**
     * Deletes all records and their associated records from the database
     */
    public void deleteAll();

}
