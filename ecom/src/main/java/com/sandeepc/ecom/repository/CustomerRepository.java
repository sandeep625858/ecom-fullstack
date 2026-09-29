package com.sandeepc.ecom.repository;

import com.sandeepc.ecom.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByEmailOrMobileNumber(String email,String mobileNumber);
}
