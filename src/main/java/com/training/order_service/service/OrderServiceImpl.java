package com.training.order_service.service;

import com.training.order_service.common.Payment;
import com.training.order_service.common.TransactionRequest;
import com.training.order_service.common.TransactionResponse;
import com.training.order_service.dto.OrderEntityDto;
import com.training.order_service.entity.OrderEntity;
import com.training.order_service.exception.NoDataFoundException;
import com.training.order_service.respository.OrderRepository;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RefreshScope
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    @Lazy
    RestTemplate restTemplate;
    @Autowired
    ModelMapper modelMapper;

    @Value("${microservice.payment-service.endpoint:0}")
    private String PAYMENT_URL;


    @Override
    public TransactionResponse orderSave(TransactionRequest transactionRequest) {
        // rest call to payment
        System.out.println("PAYMENT_URL IS: "+PAYMENT_URL);
        transactionRequest.getPayment().setTransactionId(UUID.randomUUID().toString());
        Payment payment= restTemplate.postForObject(PAYMENT_URL,
                transactionRequest.getPayment(),
                Payment.class);
        transactionRequest.getOrderEntityDto().setPaymentId(payment.getPaymentId());

        // DTO to Entity

        OrderEntity orderEntityRequest=modelMapper.map(transactionRequest.getOrderEntityDto(),OrderEntity.class);
        OrderEntity orderEntityResponse= orderRepository.save(orderEntityRequest);

        // Entity to DTO

        OrderEntityDto orderEntityDto=modelMapper.map(orderEntityResponse,OrderEntityDto.class);
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
