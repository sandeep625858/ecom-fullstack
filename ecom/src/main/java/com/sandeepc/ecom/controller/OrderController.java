package com.sandeepc.ecom.controller;

import com.sandeepc.ecom.dto.OrderRequestDto;
import com.sandeepc.ecom.dto.OrderResponseDto;
import com.sandeepc.ecom.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDto requestDto){
        orderService.createOrder(requestDto);
        return ResponseEntity.ok("Order placed successfully");
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> loadCustomerOrders(){
        return ResponseEntity.ok(orderService.getCustomerOrders());
    }

}
