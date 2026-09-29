package com.sandeepc.ecom.controller;

import com.sandeepc.ecom.dto.ProductDto;
import com.sandeepc.ecom.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final IProductService iProductService;

    @GetMapping
    public List<ProductDto> getProducts(){

        List<ProductDto> allProducts = iProductService.getProducts();
        return allProducts;

    }
}
