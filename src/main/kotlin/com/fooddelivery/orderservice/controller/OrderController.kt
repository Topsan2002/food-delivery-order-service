package com.fooddelivery.orderservice.controller

import com.fooddelivery.orderservice.dto.OrderRequestBodyDto
import com.fooddelivery.orderservice.dto.OrderResponseDto
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


    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable("orderId") orderId: String): ResponseEntity<Any> {
        return ResponseEntity.ok().body(orderService.getOrderById(orderId.toLong()))
    }

    @GetMapping("/customer/{customer-id}")
    fun getOrdersCustomer(@PathVariable("customer-id") cusId: String): ResponseEntity<Any?> {
        return  ResponseEntity.ok().body(orderService.getOrderByCustomerId(cusId.toLong()))
    }
    @GetMapping("/rider/{rider-id}")
    fun getOrdersRider(@PathVariable("rider-id") id: String): ResponseEntity<Any?> {
        return  ResponseEntity.ok().body(orderService.getOrderByRiderId(id.toLong()))
    }

    @GetMapping("/restaurant/{restaurant-id}")
    fun getOrdersRestaurant(@PathVariable("restaurant-id") id: String): ResponseEntity<Any?> {
        return  ResponseEntity.ok().body(orderService.getOrderByRestaurantId(id.toLong()))
    }

    @PostMapping("")
    fun createOrder(@Valid @RequestBody order: OrderRequestBodyDto): ResponseEntity<Any> {
        return ResponseEntity.ok().body(orderService.createOrder(order))
    }

    @PutMapping("/restaurant/status/{order-id}")
    fun updateOrderStatusRestaurant(@PathVariable("order-id") orderId: String): ResponseEntity<Any> {
        return  ResponseEntity.ok(orderService.updateOrderStatus(orderId))
    }

    @PutMapping("/rider/{rider-id}/status/{order-id}")
    fun updateOrderStatusRider(@PathVariable("rider-id")  riderId: String, @PathVariable("order-id") orderId: String): ResponseEntity<Any> {
        return  ResponseEntity.ok(orderService.updateOrderStatusRider(orderId, riderId))
    }

    @PutMapping("/customer/cancel/{order-id}")
    fun updateOrderStatusCustomer(@PathVariable("order-id") orderId: String): ResponseEntity<Any> {
        return  ResponseEntity.ok(orderService.updateOrderCancel(orderId, "CUSTOMER"))
    }

    @PutMapping("/restaurant/cancel/{order-id}")
    fun updateOrderCancelRestaurant(@PathVariable("order-id") orderId: String): ResponseEntity<Any> {
        return  ResponseEntity.ok(orderService.updateOrderCancel(orderId,"RESTAURANT"))
    }

}