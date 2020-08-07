package com.wilfredmorgan.api.services;

import org.springframework.data.domain.AuditorAware;
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
        // Default username
        String username = "SYSTEM";
        return Optional.of(username);
    }
}
