package com.fooddelivery.orderservice.dto

import jakarta.persistence.Column
import java.time.LocalDateTime

data class MenuItemResponseDto(
    var menuItemId: Long? = null,
    var restaurantId: Long? = null,
    var menuName: String? = null,
    var menuDescription: String? = null,
    var menuPrice: String? = null,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,

    )
