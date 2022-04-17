package com.study.refill.dto.refill

import com.study.common.RefillColor

data class RefillDto(
    var id: Int? = null,
    var length: Int?,
    var color: RefillColor?,
    var price: Int?,
    var orderId: Int?
)