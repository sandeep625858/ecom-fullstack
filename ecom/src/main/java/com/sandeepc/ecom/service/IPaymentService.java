package com.sandeepc.ecom.service;

import com.sandeepc.ecom.dto.PaymentIntentRequestDto;
import com.sandeepc.ecom.dto.PaymentIntentResponseDto;

public interface IPaymentService {

    PaymentIntentResponseDto createPaymentIntent(PaymentIntentRequestDto requestDto);

}
