package com.fooddelivery.orderservice.entity

import com.fooddelivery.orderservice.dto.OrderResponseDto
import jakarta.persistence.*
import lombok.*
import java.time.LocalDateTime

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "`order`")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var orderId: Long? = null,

    @Column(name = "customer_id")
    var customerId: Long,

    @Column(name = "restaurant_id")
    var restaurantId: Long,

    @Column(name = "rider_id")
    var riderId: Long?,

    @Column(name = "order_total")
    var orderTotal: Float,

    @Column(name = "order_status")
    var orderStatus: String,


    @Column(name = "create_at")
    val createdAt: LocalDateTime,

    @Column(name = "update_at")
    var updatedAt: LocalDateTime,

    @OneToMany(mappedBy = "orderId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var orderItems: List<OrderItem> = mutableListOf(),
    @OneToMany(mappedBy = "orderId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var orderStatusList: List<OrderStatus> = mutableListOf()
)
fun Order.toOrderResponseDto(
    customerName: String,
    restaurantName: String,
    riderName: String?,
    getMenuItemName: (Long) -> String? // A lambda function to fetch menu name by menuItemId
): OrderResponseDto {
    return OrderResponseDto(
        orderId = this.orderId,
        customerId = this.customerId,
        customerName = customerName,
        restaurantId = this.restaurantId,
        restaurantName = restaurantName,
        riderId = this.riderId,
        riderName = riderName,
        orderTotal = this.orderTotal,
        orderStatus = this.orderStatus,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        orderItems = this.orderItems.map {
            it.toOrderItemResponseDto(getMenuItemName(it.menuItemId))
        }, // Map each OrderItem to OrderItemResponseDto with the menuName
        orderStatusList = this.orderStatusList
    )
}