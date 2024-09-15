package com.fooddelivery.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients

class FoodDeliveryOrderServiceApplication

fun main(args: Array<String>) {
    runApplication<FoodDeliveryOrderServiceApplication>(*args)
}
