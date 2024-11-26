package com.abdelqodous.ecommerce.order;

import com.abdelqodous.ecommerce.customer.CustomerClient;
import com.abdelqodous.ecommerce.exception.BusinessException;
import com.abdelqodous.ecommerce.orderline.OrderLine;
import com.abdelqodous.ecommerce.orderline.OrderLineRequest;
import com.abdelqodous.ecommerce.orderline.OrderLineService;
import com.abdelqodous.ecommerce.product.ProductClient;
import com.abdelqodous.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest request) {
        //check the customer -> OpenFeign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can not create an order:: No customer exist with ID:: " +request.customerId()));

        //purchase the products -> product-microservice
        productClient.purchaseProduct(request.products());

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

        //send the order confirmation -> notification-microservice(Kafka)
        return null;
    }
}
