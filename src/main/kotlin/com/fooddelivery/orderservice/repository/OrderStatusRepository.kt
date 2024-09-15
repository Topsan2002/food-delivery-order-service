package com.fooddelivery.orderservice.repository

import com.fooddelivery.orderservice.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository

interface OrderStatusRepository: JpaRepository<OrderStatus, Long> {
}