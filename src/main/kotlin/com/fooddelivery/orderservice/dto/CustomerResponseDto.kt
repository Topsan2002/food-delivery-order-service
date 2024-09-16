package com.fooddelivery.orderservice.dto

@JvmRecord
data class CustomerResponseDto(
    val id: String,
    val firstname: String,
    val lastname: String,
    val email: String
)