package com.fooddelivery.orderservice.dto

import jakarta.persistence.Column
import java.time.LocalDateTime

data class OrderItemResponseDto(
    var orderItemId: Long?,
    val orderId: Long,
    var menuItemId: Long,
    var menuName: String?,
    var detail: String,
    var quantity: Int,
    var price: Double,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)
