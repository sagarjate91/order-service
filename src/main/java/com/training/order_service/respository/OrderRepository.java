package com.training.order_service.respository;

import com.training.order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    Optional<OrderEntity> findByProductName(String productName);
}
