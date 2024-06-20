package com.training.order_service.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Integer paymentId;
    private String paymentStatus;
    private String transactionId;


}
