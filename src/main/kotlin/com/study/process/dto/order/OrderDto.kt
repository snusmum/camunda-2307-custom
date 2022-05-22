package com.study.process.dto.order

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.study.common.OrderStatus
import com.study.common.RefillColor

@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderDto (
    var id: Int?,
    var length: Int?,
    var diameter: Int?,
    var color: RefillColor?,
    var status: OrderStatus?
)
