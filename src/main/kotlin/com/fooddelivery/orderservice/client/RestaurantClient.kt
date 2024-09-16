package com.fooddelivery.orderservice.client

import com.fooddelivery.orderservice.dto.MenuItemResponseDto
import com.fooddelivery.orderservice.dto.RestaurantResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "food-delivery-restaurant-service", url = "\${application.config.restaurant-url}")
interface RestaurantClient {
    @GetMapping("/menu/{id}")
    fun getMenuById(@PathVariable("id") id: String) : MenuItemResponseDto?
    @GetMapping("/{id}")
    fun getRestaurantById(@PathVariable("id") id: String) : RestaurantResponseDto?
}
