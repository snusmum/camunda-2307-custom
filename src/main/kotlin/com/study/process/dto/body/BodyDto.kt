package com.study.process.dto.body

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class BodyDto(
    var id: Int?,
    var price: Int?,
)