package com.abdelqodous.ecommerce.kafka;

import com.abdelqodous.ecommerce.customer.CustomerResponse;
import com.abdelqodous.ecommerce.order.PaymentMethod;
import com.abdelqodous.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
