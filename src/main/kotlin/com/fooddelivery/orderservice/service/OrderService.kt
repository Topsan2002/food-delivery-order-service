package com.fooddelivery.orderservice.service

import com.fooddelivery.orderservice.entity.Order
import com.fooddelivery.orderservice.entity.OrderItem
import com.fooddelivery.orderservice.entity.OrderStatus
import com.fooddelivery.orderservice.client.CustomerClient
import com.fooddelivery.orderservice.client.RestaurantClient
import com.fooddelivery.orderservice.client.RiderClient
import com.fooddelivery.orderservice.dto.*
import com.fooddelivery.orderservice.entity.toOrderResponseDto
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
    private val customerClient: CustomerClient,
    private val restaurantClient: RestaurantClient,
    private val riderClient: RiderClient
) {
    @Autowired
    lateinit var orderRepository: OrderRepository
    @Autowired
    lateinit var orderIemRepository: OrderItemlRepository
    @Autowired
    lateinit var orderStatusRepository: OrderStatusRepository


    fun getOrderByCustomerId(id: Long): List<OrderResponseDto> {
        val data =  orderRepository.findOrderByCustomerId(id)
        val orderData = data!!.map { it ->
            val  restaurantData =  restaurantClient.getRestaurantById(it.restaurantId.toString())
            val  customerData = customerClient.findCustomerById(it.customerId.toString())
            val  riderData =  riderClient.getRiderById(it.riderId.toString())
             it.toOrderResponseDto(
                customerName = "${customerData!!.firstname} ${customerData.lastname}",
                restaurantName = restaurantData!!.name!!,
                riderName = "${riderData?.firstname} ${riderData?.lastname}",
                getMenuItemName = { menuItemId ->
                    restaurantClient.getMenuById(menuItemId.toString())!!.menuName
                }
            )
        }
        return orderData
    }

    fun getOrderByRestaurantId(id: Long): List<OrderResponseDto> {
        val data =  orderRepository.findOrderByRestaurantId(id)
        val orderData = data!!.map { it ->
            val  restaurantData =  restaurantClient.getRestaurantById(it.restaurantId.toString())
            val  customerData = customerClient.findCustomerById(it.customerId.toString())
            val  riderData =  riderClient.getRiderById(it.riderId.toString())
            it.toOrderResponseDto(
                customerName = "${customerData!!.firstname} ${customerData.lastname}",
                restaurantName = restaurantData!!.name!!,
                riderName = "${riderData?.firstname} ${riderData?.lastname}",
                getMenuItemName = { menuItemId ->
                    restaurantClient.getMenuById(menuItemId.toString())!!.menuName
                }
            )
        }
        return orderData
    }

    fun getOrderByRiderId(id: Long): List<OrderResponseDto> {
        val data =  orderRepository.findOrderByRiderId(id)
        val orderData = data!!.map { it ->
            val  restaurantData =  restaurantClient.getRestaurantById(it.restaurantId.toString())
            val  customerData = customerClient.findCustomerById(it.customerId.toString())
            val  riderData =  riderClient.getRiderById(it.riderId.toString())
            it.toOrderResponseDto(
                customerName = "${customerData!!.firstname} ${customerData.lastname}",
                restaurantName = restaurantData!!.name!!,
                riderName = "${riderData?.firstname} ${riderData?.lastname}",
                getMenuItemName = { menuItemId ->
                    restaurantClient.getMenuById(menuItemId.toString())!!.menuName
                }
            )
        }
        return orderData
    }


    @Transactional
    fun createOrder(orderReq: OrderRequestBodyDto): ServiceFormat {
        try {
           customerClient.findCustomerById(orderReq.customerId.toString())
                ?: return ServiceFormat(
                    message = "Create Order Fail",
                    data = null,
                    error = "Customer not found",
                )
            val order = orderReq.toOrder()
            orderRepository.save(order)
            val orderItems = orderReq.orderItems.map { itemReq ->
                val item = itemReq.toOrderItem(order.orderId!!)
                orderIemRepository.save(item)
            }
            val currentTime = LocalDateTime.now()
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


    fun updateOrderStatus(orderId: String) : ServiceFormat {
        val orderData =  orderRepository.findOrderByOrderId(orderId.toLong())
        val currentTime =  LocalDateTime.now()
        if(orderData != null){
            if (orderData.orderStatus == "PENDING" || orderData.orderStatus == "CONFIRM") {
                orderData.orderStatus = nextStatus(orderData.orderStatus)
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
            }else{
                return ServiceFormat(
                    message = "Cannot Updated Order Status",
                    data = null,
                    error = "RESTAURANT can't be updated to next Order status, Now Status ${orderData.orderStatus}",
                )
            }
        }
        return ServiceFormat(
            message = "Updated Order Status Fail",
            data = null,
            error = "order not not found",
        )
    }
    fun updateOrderStatusRider(orderId: String, riderId:String) : ServiceFormat {
        val orderData =  orderRepository.findOrderByOrderId(orderId.toLong())
        val currentTime =  LocalDateTime.now()
        if(orderData != null ){
            if (orderData.orderStatus == "COOKING" || orderData.orderStatus == "DELIVERING"){
                orderData.orderStatus = nextStatus(orderData.orderStatus)
                orderData.riderId = riderId.toLong()
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
            }else{
                return ServiceFormat(
                    message = "Cannot Updated Order Status",
                    data = null,
                    error = "RIDER can't be updated to next Order status, Now Status ${orderData.orderStatus}",
                )
            }

        }
        return ServiceFormat(
            message = "Updated Order Status Fail",
            data = null,
            error = "order not not found",
        )
    }

    fun updateOrderCancel(orderId: String,changeBy: String ) : ServiceFormat {
        val orderData =  orderRepository.findOrderByOrderId(orderId.toLong())
        val currentTime =  LocalDateTime.now()
        if(orderData != null ){
            if(changeBy == "RESTAURANT"){

            }
            if (orderData.orderStatus == "PENDING"){
                orderData.orderStatus = "CANCEL"
                orderData.updatedAt = currentTime
                orderRepository.save(orderData)
                orderStatusRepository.save(OrderStatus(
                    orderId = orderData.orderId!!,
                    orderStatus = orderData.orderStatus,
                    changerId = if (changeBy == "RESTAURANT") orderData.restaurantId else orderData.customerId,
                    changerBy = changeBy,
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
                message = "Cannot Updated Order Status",
                data = null,
                error = "Can't be updated to Cancel, Now Status ${orderData.orderStatus}",
            )

        }
        return ServiceFormat(
            message = "Updated Order Status Fail",
            data = null,
            error = "order not not found",
        )
    }

    fun nextStatus(orderStatus: String): String {
        return when (orderStatus) {
            "PENDING" -> "CONFIRM"
            "CONFIRM" -> "COOKING"
            "COOKING" -> "DELIVERING"
            "DELIVERING" -> "COMPLETED"
            else -> throw IllegalArgumentException("Unknown status: $orderStatus")
        }
    }


    fun getOrderById(orderId: Long): ServiceFormat {
        val orderData = orderRepository.findOrderByOrderId(orderId)
        if(orderData != null ){
            val  restaurantData =  restaurantClient.getRestaurantById(orderData.restaurantId.toString())
            val  customerData = customerClient.findCustomerById(orderData.customerId.toString())
            val  riderData =  riderClient.getRiderById(orderData.riderId.toString())
            val orderResponseDto = orderData.toOrderResponseDto(
                customerName = "${customerData!!.firstname} ${customerData.lastname}",
                restaurantName = restaurantData!!.name!!,
                riderName = "${riderData?.firstname} ${riderData?.lastname}",
                getMenuItemName = { menuItemId ->
                    restaurantClient.getMenuById(menuItemId.toString())!!.menuName
                }
            )
            return ServiceFormat(
                message = "",
                data = orderResponseDto,
                error = null,
            )
        }
        return ServiceFormat(
            message = "Updated Order Status Fail",
            data = null,
            error = "order not not found",
        )
    }

}
