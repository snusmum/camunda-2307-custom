package com.study.body.model

import javax.persistence.*

@Entity
class Body(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var length: Int?,

    @Column(nullable = false)
    var diameter: Int?,

    @Column(nullable = false)
    var price: Int?,

    @Column(unique = true)
    var orderId: Int? = null
)