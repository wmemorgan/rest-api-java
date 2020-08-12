package com.wilfredmorgan.api.handlers;

import com.wilfredmorgan.api.exceptions.ResourceNotFoundException;
import com.wilfredmorgan.api.models.ValidationError;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HelpFunctions {

    /**
     * Checks to see if the authenticated user has access to modify the requested user's information
     *
     * @param username The user name of the user whose data is requested to be changed. This should either match the authenticated user
     *                 or the authenticate must have the role ADMIN
     * @return true if the user can make the modifications, otherwise an exception is thrown
     */
    public boolean isAuthorizedToMakeChange(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check to see if the user whose information being requested is the current user
        // Check to see if the requesting user is an admin
        // if either is true, return true
        // otherwise stop the process and throw an exception
        if (username.equalsIgnoreCase(authentication.getName()
            .toLowerCase()) || authentication.getAuthorities()
            .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // this user can make this change
            return true;
        } else {
            throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
        }
    }

    /**
     * Searches to see if the exception has any constraint violations to report
     *
     * @param cause the exception to search
     * @return constraint violations formatted for sending to the client
     */
    public List<ValidationError> getConstraintViolation(Throwable cause) {

        while ((cause != null) && !(cause instanceof ConstraintViolationException)) {
            cause = cause.getCause();
        }

        List<ValidationError> listVE = new ArrayList<>();

        if (cause != null) {
            ConstraintViolationException ex = (ConstraintViolationException) cause;

            for (ConstraintViolation cv : ex.getConstraintViolations()) {
                ValidationError newVE = new ValidationError();
                newVE.setCode(cv.getInvalidValue().toString());
                newVE.setMessage(cv.getMessage());
                listVE.add(newVE);
            }
        }

        return listVE;
    }
}
