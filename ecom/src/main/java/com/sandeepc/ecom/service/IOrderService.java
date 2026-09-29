package com.sandeepc.ecom.service;

import com.sandeepc.ecom.dto.OrderRequestDto;
import com.sandeepc.ecom.dto.OrderResponseDto;
import com.sandeepc.ecom.entity.Order;

import java.util.List;

public interface IOrderService {

    void createOrder(OrderRequestDto orderRequest);

    List<OrderResponseDto> getCustomerOrders();

    List<OrderResponseDto> getAllPendingOrders();

    Order updateOrderStatus(Long orderId, String orderStatus);

}
