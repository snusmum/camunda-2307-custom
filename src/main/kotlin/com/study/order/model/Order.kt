package com.study.order.model

import com.study.common.OrderStatus
import com.study.common.RefillColor
import javax.persistence.*

@Entity
@Table(name = "pen_order")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var length: Int?,

    @Column(nullable = false)
    var diameter: Int?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var color: RefillColor? = null,

    var price: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus?
)