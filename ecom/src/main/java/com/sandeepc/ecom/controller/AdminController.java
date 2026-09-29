package com.sandeepc.ecom.controller;

import com.sandeepc.ecom.constant.ApplicationConstants;
import com.sandeepc.ecom.dto.ContactResponseDto;
import com.sandeepc.ecom.dto.OrderResponseDto;
import com.sandeepc.ecom.dto.ResponseDto;
import com.sandeepc.ecom.entity.Order;
import com.sandeepc.ecom.service.IContactService;
import com.sandeepc.ecom.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IOrderService orderService;
    private final IContactService contactService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllPendingOrders(){
        return ResponseEntity.ok(orderService.getAllPendingOrders());
    }

    @PatchMapping("/orders/{orderId}/confirm")
    public ResponseEntity<ResponseDto> confirmOrder(@PathVariable Long orderId){
        Order confirmedOrder = orderService.updateOrderStatus(orderId, ApplicationConstants.ORDER_STATUS_CONFIRMED);
        return ResponseEntity.ok(
                new ResponseDto("200" , "Order #"+ confirmedOrder.getOrderId()+ "has been confirmed.")
        );
    }

    @PatchMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ResponseDto> cancelOrder(@PathVariable Long orderId){
        Order confirmedOrder = orderService.updateOrderStatus(orderId, ApplicationConstants.ORDER_STATUS_CANCELLED);
        return ResponseEntity.ok(
                new ResponseDto("200" , "Order #"+ confirmedOrder.getOrderId()+ "has been cancelled.")
        );
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ContactResponseDto>> getAllOpenMessages(){
        return ResponseEntity.ok(contactService.getAllOpenMessages());
    }

    @PatchMapping("/messages/{contactId}/close")
    public ResponseEntity<ResponseDto> closeMessage(@PathVariable Long contactId){
        contactService.updateMessageStatus(contactId, ApplicationConstants.CLOSED_MESSAGE);
        return ResponseEntity.ok(
                new ResponseDto("200" , "Contact #"+ contactId + "has been closed.")
        );
    }

}
