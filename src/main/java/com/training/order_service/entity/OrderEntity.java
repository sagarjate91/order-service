package com.training.order_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDER_TB")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Integer orderId;
    private String productName;
    private int quantity;
    private double price;
    private Integer paymentId;

}
