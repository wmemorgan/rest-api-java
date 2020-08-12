package com.wilfredmorgan.api.services;

import com.wilfredmorgan.api.handlers.HelpFunctions;
import com.wilfredmorgan.api.models.Role;
import com.wilfredmorgan.api.models.User;
import com.wilfredmorgan.api.models.UserRoles;
import com.wilfredmorgan.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilfredmorgan.api.exceptions.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements UserService interface
 */
@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    /**
     * Connects this service to the User model
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Connect this service to the Role model
     */
    @Autowired
    RoleService roleService;

    @Autowired
    HelpFunctions helpFunctions;

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        /**
         * findAll returns an iterator set
         * Iterate over the iterator set and add each element to the array list
         */
        userRepository.findAll()
                .iterator()
                .forEachRemaining(list::add);

        return list;
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User id " + id + " not found"
                ));
    }

    @Override
    public User findByUsername(String username) {
        User u = userRepository.findByUsernameIgnoreCase(username);

        if (u == null) {
            throw new ResourceNotFoundException(
                    "Username " + username + " not found"
            );
        }

        return u;

    }

    @Override
    public User findByEmail(String email) {
        User u = userRepository.findByPrimaryemailIgnoreCase(email);

        if (u == null) {
            throw new ResourceNotFoundException(
                    "User email " + email + " not found"
            );
        }

        return u;
    }

    @Override
    public List<User> findUsernamesContaining(String username) {
        List<User> list = new ArrayList<>();

        userRepository.findByUsernameContainingIgnoreCase(username)
                .iterator()
                .forEachRemaining(list::add);

        return list;
    }

    @Override
    public List<User> findAllByLastName(String lastname) {
        List<User> list = new ArrayList<>();

        userRepository.findByLastnameContainingIgnoreCase(lastname)
                .iterator()
                .forEachRemaining(list::add);

        return list;
    }

    @Override
    public User save(User user) {
        User newUser = new User();
        // Determine if record exists to be replaced
        if (user.getUserid() != 0) {
            findById(user.getUserid());
            newUser.setUserid(user.getUserid());
        }

        // Populate object fields
        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setPrimaryemail(user.getPrimaryemail().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());

        // Populate assigned roles
        newUser.getRoles().clear();

        //
        if (user.getRoles().size() > 0) {
            for (UserRoles ur : user.getRoles()) {
                Role addRole = roleService.findRoleById(ur.getRole().getRoleid());

                newUser.getRoles()
                        .add(new UserRoles(newUser, addRole));
            }
        } else {
            Role r = roleService.findRoleByName("user");
            newUser.getRoles().add(new UserRoles(newUser, r));
        }

        return userRepository.save(newUser);
    }

    @Override
    public User update(User user, long id) {
        // Confirm user exists
        User u = findById(id);

        if (helpFunctions.isAuthorizedToMakeChange(u.getUsername())) {
            // Update fields with provided data
            if (user.getUsername() != null) {
                u.setUsername(user.getUsername().toLowerCase());
            }

            if (user.getFirstname() != null) {
                u.setFirstname(user.getFirstname());
            }

            if (user.getLastname() != null) {
                u.setLastname(user.getLastname());
            }

            if (user.getPrimaryemail() != null) {
                u.setPrimaryemail(user.getPrimaryemail().toLowerCase());
            }

            if (user.getPassword() != null) {
                u.setPasswordNoEncrypt(user.getPassword());
            }

            // Update role assignments
            if (user.getRoles().size() > 0) {
                u.getRoles().clear();

                for (UserRoles ur : user.getRoles()) {
                    Role addRole = roleService.findRoleById(ur.getRole().getRoleid());

                    u.getRoles()
                            .add(new UserRoles(u, addRole));
                }
            }

            return userRepository.save(u);
        } else {
            throw new ResourceNotFoundException("Not authorized");
        }
    }

    @Override
    public void delete(long id) {
        // Confirm user exists
        findById(id);

        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
