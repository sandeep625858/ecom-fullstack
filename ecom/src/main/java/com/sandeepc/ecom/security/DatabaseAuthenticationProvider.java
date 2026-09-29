package com.sandeepc.ecom.security;

import com.sandeepc.ecom.entity.Customer;
import com.sandeepc.ecom.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Customer customer= customerRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User details not found for user: "+username)
        );
        if(passwordEncoder.matches(password,customer.getPasswordHash())){
            return new UsernamePasswordAuthenticationToken(customer,null,
                    customer.getRoles().stream()
                            .map(role -> (GrantedAuthority) role::getName)
                            .toList());
        }else{
            throw new BadCredentialsException("Invalid password!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
