package com.training.order_service.service;

import com.training.order_service.common.TransactionRequest;
import com.training.order_service.common.TransactionResponse;
import com.training.order_service.entity.OrderEntity;
import com.training.order_service.exception.NoDataFoundException;

import java.util.List;

public interface OrderService {

    TransactionResponse orderSave(TransactionRequest transactionRequest);
    OrderEntity getOrder(String productName)throws NoDataFoundException;
    List<OrderEntity> getOrders();

}
