package com.study.body.api.mapper

import com.study.body.dto.BodyDto
import com.study.body.model.Body
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface BodyMapper {

    fun toDto(body: Body): BodyDto

    fun toListDto(findAll: MutableIterable<Body>): List<BodyDto>

}