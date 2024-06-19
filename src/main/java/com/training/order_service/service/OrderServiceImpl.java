package com.training.order_service.service;

import com.training.order_service.entity.OrderEntity;
import com.training.order_service.exception.NoDataFoundException;
import com.training.order_service.respository.OrderRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public String orderSave(OrderEntity orderEntity) {
        return ObjectUtils.isNotEmpty(orderRepository.save(orderEntity))
                ?"Order is added successfully"
                :"Order is not added successfully";
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
