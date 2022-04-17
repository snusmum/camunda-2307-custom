package com.study.process.dto.refill

import com.study.common.RefillColor

data class BookRefillDto(
    var orderId: Int?,
    var length: Int?,
    var color: RefillColor?,
)