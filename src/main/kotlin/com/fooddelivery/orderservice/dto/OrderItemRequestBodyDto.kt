package com.fooddelivery.orderservice.dto

import jakarta.persistence.Column
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class OrderItemRequestBodyDto(
    @field:NotNull(message = "Menu item ID must not be null")
    val menuItemId: Long?,

//    @field:NotBlank(message = "Detail must not be blank")
    val detail: String,

    @field:Positive(message = "Quantity must be greater than 0")
    @field:NotNull(message = "Quantity must be greater than 0")
    val quantity: Int?,

    @field:Positive(message = "Price must be greater than 0")
    @field:NotNull(message = "Price must be greater than 0")
    val price: Double?,
)
