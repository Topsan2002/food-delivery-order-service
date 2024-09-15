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
@Table(name = "order_item")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    var orderItemId: Long? = null,

    @Column(name = "order_id")
    val orderId: Long,

    @Column(name = "menu_item_id")
    var menuItemId: Long,

    @Column(name = "detail")
    var detail: String,

    @Column(name = "quantity")
    var quantity: Int,

    @Column(name = "price")
    var price: Double,

    @Column(name = "create_at")
    val createdAt: LocalDateTime,

    @Column(name = "update_at")
    var updatedAt: LocalDateTime
)
