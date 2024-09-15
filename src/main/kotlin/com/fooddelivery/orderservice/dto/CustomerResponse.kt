package com.fooddelivery.orderservice.dto

@JvmRecord
data class CustomerResponse(
    val id: String,
    val firstname: String,
    val lastname: String,
    val email: String
)