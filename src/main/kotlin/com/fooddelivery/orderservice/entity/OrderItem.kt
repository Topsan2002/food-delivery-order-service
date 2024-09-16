package com.fooddelivery.orderservice.entity

import com.fooddelivery.orderservice.dto.OrderItemResponseDto
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
fun OrderItem.toOrderItemResponseDto(menuName: String?): OrderItemResponseDto {
    return OrderItemResponseDto(
        orderItemId = this.orderItemId,
        orderId = this.orderId,
        menuItemId = this.menuItemId,
        menuName = menuName,
        detail = this.detail,
        quantity = this.quantity,
        price = this.price,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}