package com.wilfredmorgan.api.controllers;

import com.wilfredmorgan.api.models.User;
import com.wilfredmorgan.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(value = "/user/email/{email}", produces = {"application/json"})
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User u = userService.findByEmail(email);

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
    @GetMapping(value = "/lastname/like/{lastname}", produces = {"application/json"})
    public ResponseEntity<?> getAllUsersByLastName(@PathVariable String lastname) {
        List<User> users = userService.findAllByLastName(lastname);
        
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
