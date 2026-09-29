package com.sandeepc.ecom.service.impl;

import com.sandeepc.ecom.constant.ApplicationConstants;
import com.sandeepc.ecom.dto.OrderItemReponseDto;
import com.sandeepc.ecom.dto.OrderRequestDto;
import com.sandeepc.ecom.dto.OrderResponseDto;
import com.sandeepc.ecom.entity.Customer;
import com.sandeepc.ecom.entity.Order;
import com.sandeepc.ecom.entity.OrderItem;
import com.sandeepc.ecom.entity.Product;
import com.sandeepc.ecom.exception.ResourceNotFoundException;
import com.sandeepc.ecom.repository.OrderRepository;
import com.sandeepc.ecom.repository.ProductRepository;
import com.sandeepc.ecom.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProfileServiceImpl profileService;

    @Override
    public void createOrder(OrderRequestDto orderRequest) {

        Customer customer = profileService.getAuthenticatedCustomer();
        Order order = new Order();
        order.setCustomer(customer);
        BeanUtils.copyProperties(orderRequest, order);
        order.setOrderStatus(ApplicationConstants.ORDER_STATUS_CREATED);
        List<OrderItem> orderItems = orderRequest.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID",
                            item.productId().toString()));
            orderItem.setProduct(product);
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(item.price());
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDto> getCustomerOrders() {
        Customer customer = profileService.getAuthenticatedCustomer();
        List<Order> orders = orderRepository.findByCustomerOrderByCreatedAtDesc(customer);
        return orders.stream().map(this::mapOrderToOrderResponse).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getAllPendingOrders() {
        List<Order> pendingOrders = orderRepository.findByOrderStatus(ApplicationConstants.ORDER_STATUS_CREATED);
        return pendingOrders.stream().map(this::mapOrderToOrderResponse).collect(Collectors.toList());
    }

    @Override
    public Order updateOrderStatus(Long orderId, String orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order","OrderId",orderId.toString())
        );
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    private OrderResponseDto mapOrderToOrderResponse(Order order){
        List<OrderItemReponseDto> itemList = order.getOrderItems().stream()
                .map(this::mapToOrderItemResponseDto).collect(Collectors.toList());
        return new OrderResponseDto(order.getOrderId(), order.getOrderStatus(), order.getTotalPrice(),
                order.getCreatedAt().toString(),itemList);
    }

    private OrderItemReponseDto mapToOrderItemResponseDto(OrderItem orderItem) {

        return new OrderItemReponseDto(orderItem.getProduct().getName(), orderItem.getQuantity(),
                orderItem.getPrice(),orderItem.getProduct().getImageUrl());

    }
}
