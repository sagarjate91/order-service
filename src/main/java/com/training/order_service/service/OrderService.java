package com.training.order_service.service;

import com.training.order_service.entity.OrderEntity;
import com.training.order_service.exception.NoDataFoundException;

import java.util.List;

public interface OrderService {

    String orderSave(OrderEntity orderEntity);
    OrderEntity getOrder(String productName)throws NoDataFoundException;
    List<OrderEntity> getOrders();

}
