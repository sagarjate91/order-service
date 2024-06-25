package com.training.order_service.service;

import com.training.order_service.common.Payment;
import com.training.order_service.common.TransactionRequest;
import com.training.order_service.common.TransactionResponse;
import com.training.order_service.dto.OrderEntityDto;
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
        transactionRequest.getOrderEntityDto().setPaymentId(payment.getPaymentId());

        // DTO to Entity
        OrderEntity orderEntityRequest=OrderEntity.builder()
                .orderId(transactionRequest.getOrderEntityDto().getOrderId())
                .price(transactionRequest.getOrderEntityDto().getPrice())
                .paymentId(transactionRequest.getOrderEntityDto().getPaymentId())
                .quantity(transactionRequest.getOrderEntityDto().getQuantity())
                .productName(transactionRequest.getOrderEntityDto().getProductName())
                .build();

        OrderEntity orderEntityResponse= orderRepository.save(orderEntityRequest);

        // Entity to DTO
        OrderEntityDto orderEntityDto=OrderEntityDto.builder()
                .orderId(orderEntityResponse.getOrderId())
                .paymentId(orderEntityResponse.getPaymentId())
                .price(orderEntityResponse.getPrice())
                .productName(orderEntityResponse.getProductName())
                .quantity(orderEntityResponse.getQuantity())
                .build();
        return TransactionResponse.builder().orderEntityDto(orderEntityDto).payment(payment).build();
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
