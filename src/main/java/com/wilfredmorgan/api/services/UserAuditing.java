package com.wilfredmorgan.api.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Identify current user for auditing database record manipulation
 */
@Component
public class UserAuditing implements AuditorAware<String> {

    /**
     * Current user
     *
     * @return Optional (String) current username
     */
    @Override
    public Optional<String> getCurrentAuditor() {

        String username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            username = authentication.getName();
        } else {
            // Default username
            username = "SYSTEM";
        }

        return Optional.of(username);
    }
}
