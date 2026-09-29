package com.sandeepc.ecom.controller;

import com.sandeepc.ecom.dto.*;
import com.sandeepc.ecom.entity.Customer;
import com.sandeepc.ecom.repository.CustomerRepository;
import com.sandeepc.ecom.repository.RoleRepository;
import com.sandeepc.ecom.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto){
        try{
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.username(),loginRequestDto.password()));
            var userDto = new UserDto();
            var loggedUser = (Customer) authentication.getPrincipal();
            BeanUtils.copyProperties(loggedUser,userDto);
            userDto.setRoles(authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
            if (loggedUser.getAddress() != null) {
                AddressDto address = new AddressDto();
                BeanUtils.copyProperties(loggedUser.getAddress(),address);
                userDto.setAddress(address);
            }
            String jwtToken = jwtUtil.generateJwtToken(authentication);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),userDto,jwtToken));
        }catch(BadCredentialsException ex){
            return buildErrorResponse(HttpStatus.UNAUTHORIZED,
                    "Invalid username or password");
        }catch(AuthenticationException ex){
            return buildErrorResponse(HttpStatus.UNAUTHORIZED,
                    "Authentication failed");
        }catch(Exception ex){
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto){

        var decision = compromisedPasswordChecker.check(registerRequestDto.getPassword());
        if(decision.isCompromised()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("password","Choose a strong password"));
        }
        Optional<Customer> existingRecord = customerRepository.findByEmailOrMobileNumber(registerRequestDto.getEmail(),
                registerRequestDto.getMobileNumber());
        if(existingRecord.isPresent()){
            Map<String,String> errors = new HashMap<>();
            Customer existingCustomer = existingRecord.get();
            if(existingCustomer.getEmail().equalsIgnoreCase(registerRequestDto.getEmail())){
                errors.put("email","Email is already registered!");
            }
            if(existingCustomer.getMobileNumber().equalsIgnoreCase(registerRequestDto.getMobileNumber())){
                errors.put("mobileNumber","Mobile Number is already registered!");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(registerRequestDto,customer);
        customer.setPasswordHash(passwordEncoder.encode(registerRequestDto.getPassword()));
        roleRepository.findByName("ROLE_USER").ifPresent(role->customer.setRoles(Set.of(role)));
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
    }

    private ResponseEntity<LoginResponseDto> buildErrorResponse(
            HttpStatus status,String message){
        return ResponseEntity.status(status)
                .body(new LoginResponseDto(message,null,null));
    }

}
