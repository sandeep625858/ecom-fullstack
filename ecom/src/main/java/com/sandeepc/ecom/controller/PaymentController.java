package com.sandeepc.ecom.controller;

import com.sandeepc.ecom.dto.PaymentIntentRequestDto;
import com.sandeepc.ecom.dto.PaymentIntentResponseDto;
import com.sandeepc.ecom.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final IPaymentService iPaymentService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentIntentResponseDto> createPaymentIntent(@RequestBody PaymentIntentRequestDto requestDto){
        PaymentIntentResponseDto responseDto = iPaymentService.createPaymentIntent(requestDto);
        return ResponseEntity.ok(responseDto);

    }

}
