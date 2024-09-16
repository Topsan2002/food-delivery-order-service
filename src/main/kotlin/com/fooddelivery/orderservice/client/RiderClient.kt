package com.fooddelivery.orderservice.client

import com.fooddelivery.orderservice.dto.RiderResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "food-delivery-rider-service", url = "\${application.config.rider-url}")
interface RiderClient {
    @GetMapping("/{id}")
    fun getRiderById(@PathVariable("id") riderId: String): RiderResponseDto?
}