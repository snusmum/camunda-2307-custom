package com.study.process.dto.refill

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RefillDto(
    var id: Int?,
    var price: Int?,
)