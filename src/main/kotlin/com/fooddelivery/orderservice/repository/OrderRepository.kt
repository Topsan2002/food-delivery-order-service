package com.fooddelivery.orderservice.repository

import com.fooddelivery.orderservice.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findOrderByCustomerId(customerId: Long): List<Order>?
    fun findOrderByOrderId(orderId: Long): Order?
    fun findOrderByRestaurantId(id : Long): List<Order>?
    fun findOrderByRiderId(id: Long): List<Order>?
}

