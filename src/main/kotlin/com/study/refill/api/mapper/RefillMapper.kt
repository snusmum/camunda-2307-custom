package com.study.refill.api.mapper

import com.study.refill.dto.refill.RefillDto
import com.study.refill.model.Refill
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface RefillMapper {

    fun toDto(refill: Refill): RefillDto

    fun toListDto(findAll: MutableIterable<Refill>): List<RefillDto>

}