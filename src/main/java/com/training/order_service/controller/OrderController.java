package com.training.order_service.controller;

import com.training.order_service.common.TransactionRequest;
import com.training.order_service.common.TransactionResponse;
import com.training.order_service.entity.OrderEntity;
import com.training.order_service.exception.NoDataFoundException;
import com.training.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<TransactionResponse> orderSave(@RequestBody TransactionRequest transactionRequest){
        return ResponseEntity.ok(orderService.orderSave(transactionRequest));
    }

    @GetMapping("/getOrder/{productName}")
    public ResponseEntity<OrderEntity> getOrderByProductName(@PathVariable String productName) throws NoDataFoundException {
        return ResponseEntity.ok(orderService.getOrder(productName));
    }

    @GetMapping("/getOrdersList")
    public ResponseEntity<List<OrderEntity>> getOrders(){
        return ResponseEntity.ok(orderService.getOrders());
    }


}
