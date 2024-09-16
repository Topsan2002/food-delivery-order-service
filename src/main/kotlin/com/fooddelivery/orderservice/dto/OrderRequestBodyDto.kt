package com.fooddelivery.orderservice.dto

import com.fooddelivery.orderservice.entity.Order
import com.fooddelivery.orderservice.entity.OrderItem
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.hibernate.annotations.NotFound
import java.time.LocalDateTime

data class OrderRequestBodyDto(
    @field:NotNull(message = "Customer ID must not be null")
    val customerId: Long?,

    @field:NotNull(message = "Restaurant ID must not be null")
    val restaurantId: Long?,

    @field:NotNull(message = "Rider ID must not be null")
    val riderId: Long?,

    @field:Positive(message = "Order total must be greater than 0")
    val orderTotal: Float?,

    @field:NotNull(message = "Order items must not be null")
    @field:NotEmpty(message = "Order items must not be empty")
    @field:Valid
    val orderItems: List<OrderItemRequestBodyDto> = mutableListOf()
)

fun OrderRequestBodyDto.toOrder(): Order {
    val currentTime = LocalDateTime.now()
    return Order(
        customerId = this.customerId!!,
        restaurantId = this.restaurantId!!,
        riderId = this.riderId!!,
        orderTotal = this.orderTotal!!,
        orderStatus = "PENDING",
        createdAt = currentTime,
        updatedAt = currentTime
    )
}