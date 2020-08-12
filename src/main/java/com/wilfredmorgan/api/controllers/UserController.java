package com.wilfredmorgan.api.controllers;

import com.wilfredmorgan.api.models.User;
import com.wilfredmorgan.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The entry point for clients to access user data
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Using the User service to process user data
     */
    @Autowired
    UserService userService;

    /**
     * Returns a list of all users
     * <br>Example: <a href="http://localhost:2019/users/users">http://localhost:2019/users/users</a>
     *
     * @return JSON list of all users with a status of OK
     * @see UserService#findAll() UserService.findAll()
     */
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Returns a single user based off a user id number
     * <br>Example: <a href="http://localhost:2019/users/user/2">http://localhost:2019/users/user/2</a>
     *
     * @param userid The primary key of the user you seek
     * @return JSON object of the user you seek
     * @see UserService#findById(long) UserService.findById(long)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/{userid}", produces = {"application/json"})
    public ResponseEntity<?> getUserById(@PathVariable Long userid) {
        User u = userService.findById(userid);

        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    /**
     * Return a user object based on a given username
     * <br>Example: <a href="http://localhost:2019/users/user/username/hpotter">http://localhost:2019/users/user/username/hpotter</a>
     *
     * @param username of the user (String) you seek
     * @return JSON object of the user you seek
     * @see UserService#findByUsername(String) UserService.findByUsername(String)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/username/{username}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        User u = userService.findByUsername(username);

        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    /**
     * Return a user object based on a given email
     * <br>Example: <a href="http://localhost:2019/users/user/email/srogers@example.com">http://localhost:2019/users/user/email/srogers@example.com</a>
     *
     * @param email of the user (String) you seek
     * @return JSON object of the user you seek
     * @see UserService#findByEmail(String) UserService.findByEmail(String)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/email/{email}", produces = {"application/json"})
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User u = userService.findByEmail(email);

        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping(value = "/user/profile", produces = {"application/json"})
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        User u = userService.findByUsername(authentication.getName());

        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    /**
     * Returns a list of users whose username contains the given substring
     * <br>Example: <a href="http://localhost:2019/users/username/like/da">http://localhost:2019/users/username/like/da</a>
     *
     * @param username of the user (String) you seek
     * @return JSON list of users you seek
     * @see UserService#findUsernamesContaining(String) UserService.findUsernamesContaining(String) 
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/username/like/{username}", produces = {"application/json"})
    public ResponseEntity<?> getUsersLikeUsername(@PathVariable String username) {
        List<User> users = userService.findUsernamesContaining(username);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Returns a list of users whose last name contains the given substring
     * <br>Example: <a href="http://localhost:2019/users/lastname/like/potter">http://localhost:2019/users/lastname/like/potter</a>
     *
     * @param lastname of the user (String) you seek
     * @return JSON list of users you seek
     * @see UserService#findAllByLastName(String) UserService.findAllByLastName(String)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/lastname/like/{lastname}", produces = {"application/json"})
    public ResponseEntity<?> getAllUsersByLastName(@PathVariable String lastname) {
        List<User> users = userService.findAllByLastName(lastname);
        
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     * Given a complete User Object, create a new User record and accompanying useremail records
     * and user role records.
     * <br> Example: <a href="http://localhost:2019/users/user">http://localhost:2019/users/user</a>
     *
     * @param newuser A complete new user to add including emails and roles.
     *                roles must already exist.
     * @return A location header with the URI to the newly created user and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see UserService#save(User) UserService.save(User)
     */
    @PostMapping(value = "/user", consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User newuser) throws URISyntaxException {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /**
     * Given a complete User Object
     * Given the user id, primary key, is in the User table,
     * replace the User record and Useremail records.
     * Roles are handled through different endpoints
     * <br> Example: <a href="http://localhost:2019/users/user/2">http://localhost:2019/users/user/2</a>
     *
     * @param replaceUser A complete User including all emails and roles to be used to
     *                   replace the User. Roles must already exist.
     * @param userid     The primary key of the user you wish to replace.
     * @return status of OK
     * @see UserService#save(User) UserService.save(User)
     */
    @PutMapping(value = "/user/{userid}", consumes = "application/json")
    public ResponseEntity<?> replaceUser(@Valid @RequestBody User replaceUser,
                                         @PathVariable long userid) {

        replaceUser.setUserid(userid);
        userService.save(replaceUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates the user record associated with the given id with the provided data. Only the provided fields are affected.
     * If role list is given, it replaces the original role list.
     * <br> Example: <a href="http://localhost:2019/users/user/7">http://localhost:2019/users/user/7</a>
     *
     * @param updateUser An object containing values for just the fields that are being updated. All other fields are left NULL.
     * @param id         The primary key of the user you wish to update.
     * @return A status of OK
     * @see UserService#update(User, long) UserService.update(User, long)
     */
    @PatchMapping(value = "/user/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User updateUser, @PathVariable long id) {
        userService.update(updateUser, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given user along with associated roles
     * <br>Example: <a href="http://localhost:2019/users/user/3">http://localhost:2019/users/user/3</a>
     *
     * @param id the primary key of the user you wish to delete
     * @return Status of OK
     * @see UserService#delete(long) UserService.delete(long)
     */
    @DeleteMapping(value = "/user/{id}", produces = {"application/json"})
    public ResponseEntity<?> deleteUserById(@PathVariable long id) {
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
