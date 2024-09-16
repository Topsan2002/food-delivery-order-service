package com.fooddelivery.orderservice.client

import com.fooddelivery.orderservice.dto.CustomerResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(name = "food-delivery-customer-service", url = "\${application.config.customer-url}")
interface CustomerClient {
    @GetMapping("/{id}")
    fun findCustomerById(@PathVariable("id") customerId: String?): CustomerResponseDto?
}