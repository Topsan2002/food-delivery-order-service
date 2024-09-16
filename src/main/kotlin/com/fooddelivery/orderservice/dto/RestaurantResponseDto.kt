package com.fooddelivery.orderservice.dto

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

data class RestaurantResponseDto(
    var restaurantId: Long? = null,


    var name: String? = null,


    var email: String? = null,


    var address: String? = null,


    val createdAt: LocalDateTime,


    var updatedAt: LocalDateTime,

//    @OneToMany(mappedBy = "restaurantId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var orderItems: List<MenuItemResponseDto> = mutableListOf(),

    )
