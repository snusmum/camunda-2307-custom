package com.study.order.dto.order

import com.study.common.RefillColor

data class CreateOrderDto(
    var length: Int?,
    var diameter: Int?,
    var color: RefillColor?,
)