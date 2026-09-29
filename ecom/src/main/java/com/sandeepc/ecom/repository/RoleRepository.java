package com.sandeepc.ecom.repository;

import com.sandeepc.ecom.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Cacheable("roles")
    Optional<Role> findByName(String name);

}
