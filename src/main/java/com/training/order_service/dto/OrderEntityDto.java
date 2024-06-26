package com.training.order_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntityDto {


        private Integer orderId;
        private String productName;
        private int quantity;
        private double price;
        private Integer paymentId;


}
