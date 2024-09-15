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
@Table(name = "order_status")
data class OrderStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    val orderStatusId: Long = 0,

    @Column(name = "order_id", nullable = false)
    val orderId: Long,

    @Column(name = "changer_id", nullable = false)
    val changerId: Long,

    @Column(name = "changer_by", nullable = false, length = 50)
    val changerBy: String,

    @Column(name = "order_status", nullable = false, length = 50)
    val orderStatus: String,

    @Column(name = "create_at", nullable = false)
    val createAt: LocalDateTime,

    @Column(name = "update_at", nullable = false)
    val updateAt: LocalDateTime
)

