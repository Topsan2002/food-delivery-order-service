package com.fooddelivery.orderservice.service

import com.fooddelivery.orderservice.dto.OrderRequestBodyDto
import com.fooddelivery.orderservice.dto.ServiceFormat
import com.fooddelivery.orderservice.entity.Order
import com.fooddelivery.orderservice.entity.OrderItem
import com.fooddelivery.orderservice.entity.OrderStatus
import com.fooddelivery.orderservice.repository.CustomerClient
import com.fooddelivery.orderservice.repository.OrderItemlRepository
import com.fooddelivery.orderservice.repository.OrderRepository
import com.fooddelivery.orderservice.repository.OrderStatusRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@RequiredArgsConstructor
class OrderService(
    private val customerClient: CustomerClient
) {
    @Autowired
    lateinit var orderRepository: OrderRepository
    @Autowired
    lateinit var orderIemRepository: OrderItemlRepository
    @Autowired
    lateinit var orderStatusRepository: OrderStatusRepository
    fun getOrderByCustomerId(id: Long): List<Order>? {
        return orderRepository.findOrderByCustomerId(id)
    }
    @Transactional
    fun createOrder(orderReq: OrderRequestBodyDto): ServiceFormat {
        try {
            val customer = customerClient.findCustomerById(orderReq.customerId.toString())
                ?: return ServiceFormat(
                    message = "Create Order Fail",
                    data = null,
                    error = "Customer not found",
                )
            val currentTime = LocalDateTime.now()
            val order = Order(
                customerId = orderReq.customerId!!,
                restaurantId = orderReq.restaurantId!!,
                riderId = orderReq.riderId!!,
                orderTotal = orderReq.orderTotal!!,
                orderStatus = "PENDING",
                createdAt = currentTime,
                updatedAt = currentTime
            )
            orderRepository.save(order)
            val orderItems = orderReq.orderItems.map { itemReq ->
                orderIemRepository.save(OrderItem(
                    orderId = order.orderId!!,
                    menuItemId = itemReq.menuItemId!!,
                    detail = itemReq.detail,
                    quantity = itemReq.quantity!!,
                    price = itemReq.price!!,
                    createdAt = currentTime,
                    updatedAt = currentTime
                ))
            }
            val orderStatus = OrderStatus(
                orderId = order.orderId!!,
                orderStatus = order.orderStatus,
                changerId = order.customerId,
                changerBy = "CUSTOMER",
                createAt = currentTime,
                updateAt = currentTime,
            )
            orderStatusRepository.save(orderStatus)
            order.orderItems = orderItems
            order.orderStatusList = listOf(orderStatus)
             return ServiceFormat(
                message = "Updated Order Status Successfully",
                data = order,
                error = null,
            )
        }catch (e: Exception) {
            e.printStackTrace()
        }
        return ServiceFormat(
            message = "Create Order Fail",
            data = null,
            error = "Create Order Fail",
        )
    }


    fun updateOrderStatus(orderId: String, orderStatus: String) : ServiceFormat {
        val orderData =  orderRepository.findOrderByOrderId(orderId.toLong())
        val currentTime =  LocalDateTime.now()
        if(orderData != null && checkOrderStatus(orderStatus, orderData.orderStatus)){
            orderData.orderStatus = orderStatus
            orderData.updatedAt = currentTime
            orderRepository.save(orderData)
            orderStatusRepository.save(OrderStatus(
                orderId = orderData.orderId!!,
                orderStatus = orderData.orderStatus,
                changerId = orderData.restaurantId,
                changerBy = "RESTAURANT",
                createAt = currentTime,
                updateAt = currentTime,
            ))
            return ServiceFormat(
                message = "Updated Order Status Successfully",
                data = orderData,
                error = null,
            )
        }
        return ServiceFormat(
            message = "Updated Order Status Fail",
            data = null,
            error = "order not not found",
        )
    }
    fun updateOrderStatusRider(orderId: String, orderStatus: String, riderId:String) : ServiceFormat {
        val orderData =  orderRepository.findOrderByOrderId(orderId.toLong())
        val currentTime =  LocalDateTime.now()
        if(orderData != null && checkOrderStatus(orderStatus, orderData.orderStatus)){
            orderData.orderStatus = orderStatus
            orderData.updatedAt = currentTime
            orderRepository.save(orderData)
            orderStatusRepository.save(OrderStatus(
                orderId = orderData.orderId!!,
                orderStatus = orderData.orderStatus,
                changerId = riderId.toLong(),
                changerBy = "RIDER",
                createAt = currentTime,
                updateAt = currentTime,
            ))
            return ServiceFormat(
                message = "Updated Order Status Successfully",
                data = orderData,
                error = null,
            )
        }
        return ServiceFormat(
            message = "Updated Order Status Fail",
            data = null,
            error = "order not not found",
        )
    }

    fun updateOrderStatusCustomer(orderId: String, orderStatus: String) : ServiceFormat {
        val orderData =  orderRepository.findOrderByOrderId(orderId.toLong())
        val currentTime =  LocalDateTime.now()
        if(orderData != null && checkOrderStatus(orderStatus, orderData.orderStatus)){
            orderData.orderStatus = orderStatus
            orderData.updatedAt = currentTime
            orderRepository.save(orderData)
            orderStatusRepository.save(OrderStatus(
                orderId = orderData.orderId!!,
                orderStatus = orderData.orderStatus,
                changerId = orderData.customerId,
                changerBy = "CUSTOMER",
                createAt = currentTime,
                updateAt = currentTime,
            ))
            return ServiceFormat(
                message = "Updated Order Status Successfully",
                data = orderData,
                error = null,
            )
        }
        return ServiceFormat(
            message = "Updated Order Status Fail",
            data = null,
            error = "order not not found",
        )
    }


    fun checkOrderStatus(status : String , orderStatus : String) : Boolean{
        return !(status == orderStatus || orderStatus == "CANCEL" || orderStatus == "COMPLETED")
    }

}