package com.fooddelivery.orderservice.dto

import com.fooddelivery.orderservice.entity.OrderItem
import com.fooddelivery.orderservice.entity.OrderStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

data class OrderResponseDto(
    var orderId: Long? = null,
    var customerId: Long,
    var customerName: String,
    var restaurantId: Long,
    var restaurantName: String,
    var riderId: Long,
    var riderName: String?,
    var orderTotal: Float,
    var orderStatus: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var orderItems: List<OrderItemResponseDto> = mutableListOf(),
    var orderStatusList: List<OrderStatus> = mutableListOf()
)
