package com.abdelqodous.ecommerce.payment;

import com.abdelqodous.ecommerce.customer.CustomerResponse;
import com.abdelqodous.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
//        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
