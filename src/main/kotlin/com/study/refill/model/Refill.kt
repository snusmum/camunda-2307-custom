package com.study.refill.model

import com.study.common.RefillColor
import javax.persistence.*

@Entity
class Refill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var length: Int?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var color: RefillColor?,

    @Column(nullable = false)
    var price: Int?,

    @Column(unique = true)
    var orderId: Int? = null
)