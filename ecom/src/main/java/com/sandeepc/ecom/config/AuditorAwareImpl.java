package com.sandeepc.ecom.config;

import com.sandeepc.ecom.entity.Customer;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")){
            return Optional.of("Anonymous User");
        }
        var principal = authentication.getPrincipal();
        return Optional.of(principal instanceof Customer customer ?
                customer.getEmail() : principal.toString());
    }
}
