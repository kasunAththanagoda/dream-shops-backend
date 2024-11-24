package com.ecommerce.dreamshops.repository;

import com.ecommerce.dreamshops.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
