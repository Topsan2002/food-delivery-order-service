package com.fooddelivery.orderservice.controller

import com.fooddelivery.orderservice.dto.OrderRequestBodyDto
import com.fooddelivery.orderservice.entity.Order
import com.fooddelivery.orderservice.service.OrderService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/order")
class OrderController {
    @Autowired
    private lateinit var orderService: OrderService


    @GetMapping("/customer/{customer-id}")
    fun getOrders(@PathVariable("customer-id") cusId: String): ResponseEntity<List<Order>?> {
        return  ResponseEntity.ok().body(orderService.getOrderByCustomerId(cusId.toLong()))
    }

    @PostMapping("")
    fun createOrder(@Valid @RequestBody order: OrderRequestBodyDto): ResponseEntity<Any> {
        return ResponseEntity.ok().body(orderService.createOrder(order))
    }

    @PutMapping("/restaurant/status/{order-id}/{status}")
    fun updateOrderStatusRestaurant(@PathVariable("order-id") orderId: String,@PathVariable("status") status: String): ResponseEntity<Any> {
        return  ResponseEntity.ok(orderService.updateOrderStatus(orderId,status))
    }

    @PutMapping("/rider/status/{rider-id}/{order-id}/{status}")
    fun updateOrderStatusRider(@PathVariable("rider-id")  riderId: String, @PathVariable("order-id") orderId: String,@PathVariable("status") status: String): ResponseEntity<Any> {
        return  ResponseEntity.ok(orderService.updateOrderStatusRider(orderId,status, riderId))
    }

    @PutMapping("/customer/status/{order-id}/{status}")
    fun updateOrderStatusCustomer(@PathVariable("order-id") orderId: String,@PathVariable("status") status: String): ResponseEntity<Any> {
        return  ResponseEntity.ok(orderService.updateOrderStatusCustomer(orderId,status))
    }

}