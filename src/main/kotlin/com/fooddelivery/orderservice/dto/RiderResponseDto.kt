package com.fooddelivery.orderservice.dto

import java.time.LocalDateTime

data class RiderResponseDto(
    val riderId: String?,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val phone: String?,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)
