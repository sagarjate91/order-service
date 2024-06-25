package com.training.order_service.common;

import com.training.order_service.dto.OrderEntityDto;
import com.training.order_service.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    private OrderEntityDto orderEntityDto;
    private Payment payment;
}
