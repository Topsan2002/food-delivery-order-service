package com.fooddelivery.orderservice.entity

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
    var riderId: Long,

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