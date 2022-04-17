package com.study.order.api.mapper

import com.study.order.dto.order.OrderDto
import com.study.order.model.Order
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OrderMapper {

    fun toDto(order: Order): OrderDto

    fun toListDto(findAll: MutableIterable<Order>): List<OrderDto>

}