package com.study.refill.dto.refill

import com.study.common.RefillColor

data class CreateRefillDto(
    var length: Int?,
    var color: RefillColor?,
    var price: Int?,
)