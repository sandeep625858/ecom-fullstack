package com.sandeepc.ecom.service.impl;

import com.sandeepc.ecom.dto.ProductDto;
import com.sandeepc.ecom.entity.Product;
import com.sandeepc.ecom.repository.ProductRepository;
import com.sandeepc.ecom.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Cacheable("products")
    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(this::transformDTO).toList();
    }

    private ProductDto transformDTO (Product product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }
}
