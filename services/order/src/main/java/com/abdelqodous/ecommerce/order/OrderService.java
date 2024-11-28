package com.abdelqodous.ecommerce.order;

import com.abdelqodous.ecommerce.customer.CustomerClient;
import com.abdelqodous.ecommerce.exception.BusinessException;
import com.abdelqodous.ecommerce.kafka.OrderConfirmation;
import com.abdelqodous.ecommerce.kafka.OrderProducer;
import com.abdelqodous.ecommerce.orderline.OrderLine;
import com.abdelqodous.ecommerce.orderline.OrderLineRequest;
import com.abdelqodous.ecommerce.orderline.OrderLineService;
import com.abdelqodous.ecommerce.payment.PaymentClient;
import com.abdelqodous.ecommerce.payment.PaymentRequest;
import com.abdelqodous.ecommerce.product.ProductClient;
import com.abdelqodous.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        //check the customer -> OpenFeign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create an order:: No customer exist with ID:: " +request.customerId()));

        //purchase the products -> product-microservice
        var purchasedProducts = productClient.purchaseProduct(request.products());

        //persist order
        var order = repository.save(mapper.toOrder(request));

        //persist order line
        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                        null,
                        order.getId(),
                        purchaseRequest.productId(),
                        purchaseRequest.quantity()
                    )
            );
        }

        //start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //send the order confirmation -> notification-microservice(Kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with ID:: %d", orderId)));
    }
}
