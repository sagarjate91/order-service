package com.training.order_service.service;

import com.training.order_service.common.Payment;
import com.training.order_service.common.TransactionRequest;
import com.training.order_service.common.TransactionResponse;
import com.training.order_service.entity.OrderEntity;
import com.training.order_service.exception.NoDataFoundException;
import com.training.order_service.respository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    @Override
    public TransactionResponse orderSave(TransactionRequest transactionRequest) {
        // rest call to payment
        transactionRequest.getPayment().setTransactionId(UUID.randomUUID().toString());
        Payment payment= restTemplate.postForObject("http://PAYMENT-SERVICE/payment/save",transactionRequest.getPayment(),
                Payment.class);
        transactionRequest.getOrderEntity().setPaymentId(payment.getPaymentId());
        OrderEntity orderEntity= orderRepository.save(transactionRequest.getOrderEntity());
        return TransactionResponse.builder().orderEntity(orderEntity).payment(payment).build();
    }

    @Override
    public OrderEntity getOrder(String productName) throws NoDataFoundException {
        return orderRepository.findByProductName(productName)
                .orElseThrow(()->new NoDataFoundException("Product is not available!"));
    }

    @Override
    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }
}
