package com.sandeepc.ecom.repository;

import com.sandeepc.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //optional
public interface ProductRepository extends JpaRepository<Product,Long> {
}
