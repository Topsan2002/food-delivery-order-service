package com.fooddelivery.orderservice.repository

import com.fooddelivery.orderservice.dto.CustomerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@FeignClient(name = "food-delivery-customer-service", url = "\${application.config.customer-url}")
interface CustomerClient {
    @GetMapping("/{id}")
    fun findCustomerById(@PathVariable("id") customerId: String?): CustomerResponse?
}