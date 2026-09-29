package com.sandeepc.ecom.repository;


import com.sandeepc.ecom.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //optional
public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findByStatusOrderByCreatedAtDesc(String status);
}
