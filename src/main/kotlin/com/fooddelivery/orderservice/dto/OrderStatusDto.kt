package com.fooddelivery.orderservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class OrderStatusDto(
    @field:NotEmpty(message = "status can't be empty")
    @field:NotNull(message = "status can't be empty")
    @field:NotBlank(message = "status can't be empty")
    var status: String,
)
