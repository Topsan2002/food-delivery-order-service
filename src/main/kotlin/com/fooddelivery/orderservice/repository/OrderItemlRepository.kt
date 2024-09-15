package com.fooddelivery.orderservice.repository

import com.fooddelivery.orderservice.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemlRepository : JpaRepository<OrderItem, Long> {
}