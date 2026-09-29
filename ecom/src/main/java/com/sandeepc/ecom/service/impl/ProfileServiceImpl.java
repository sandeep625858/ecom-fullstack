package com.sandeepc.ecom.service.impl;

import com.sandeepc.ecom.dto.AddressDto;
import com.sandeepc.ecom.dto.ProfileRequestDto;
import com.sandeepc.ecom.dto.ProfileResponseDto;
import com.sandeepc.ecom.entity.Address;
import com.sandeepc.ecom.entity.Customer;
import com.sandeepc.ecom.repository.CustomerRepository;
import com.sandeepc.ecom.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private final CustomerRepository customerRepository;

    @Override
    public ProfileResponseDto getProfile() {
        Customer customer = getAuthenticatedCustomer();
        return mapCustomerToProfileResponseDto(customer);
    }

    @Override
    public ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto) {
        Customer customer = getAuthenticatedCustomer();
        boolean isEmailUpdated = !customer.getEmail().equals(profileRequestDto.getEmail().trim());
        BeanUtils.copyProperties(profileRequestDto, customer);
        Address address = customer.getAddress();
        if (address == null) {
            address = new Address();
            address.setCustomer(customer);
        }
        address.setStreet(profileRequestDto.getStreet());
        address.setCity(profileRequestDto.getCity());
        address.setState(profileRequestDto.getState());
        address.setPostalCode(profileRequestDto.getPostalCode());
        address.setCountry(profileRequestDto.getCountry());
        customer.setAddress(address);
        customer = customerRepository.save(customer);
        ProfileResponseDto profileResponseDto = mapCustomerToProfileResponseDto(customer);
        profileResponseDto.setEmailUpdated(isEmailUpdated);
        return profileResponseDto;
    }

    public Customer getAuthenticatedCustomer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return customerRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

    }

    private ProfileResponseDto mapCustomerToProfileResponseDto(Customer customer){
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        BeanUtils.copyProperties(customer,profileResponseDto);
        if (customer.getAddress() != null) {
            AddressDto address = new AddressDto();
            BeanUtils.copyProperties(customer.getAddress(),address);
            profileResponseDto.setAddress(address);
        }
        return profileResponseDto;
    }


}
