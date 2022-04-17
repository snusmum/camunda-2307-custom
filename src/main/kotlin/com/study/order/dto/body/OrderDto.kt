package com.study.order.dto.order

import com.study.common.OrderStatus
import com.study.common.RefillColor

data class OrderDto(
    var id: Int? = null,
    var length: Int?,
    var diameter: Int?,
    var color: RefillColor?,
    var price: Int?,
    var status: OrderStatus?
)