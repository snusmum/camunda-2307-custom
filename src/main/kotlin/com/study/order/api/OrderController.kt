package com.study.order.api

import com.study.common.OrderStatus
import com.study.common.RefillColor
import com.study.order.api.mapper.OrderMapper
import com.study.order.dto.order.CreateOrderDto
import com.study.order.dto.order.OrderDto
import com.study.order.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService,
    private val orderMapper: OrderMapper
) {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dto: CreateOrderDto): OrderDto {
        return orderService.create(
            length = dto.length ?: 0,
            diameter = dto.diameter ?: 0,
            color = dto.color ?: RefillColor.WRONG
        )
            .let { b -> orderMapper.toDto(b) }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): OrderDto {
        return orderMapper.toDto(orderService.get(id))
    }

    @GetMapping("/list")
    fun getAll(): List<OrderDto> {
        return orderMapper.toListDto(orderService.findAll())
    }

    @PostMapping("/{id}/set-status")
    fun book(@PathVariable id: Int, @RequestBody status: OrderStatus) {
        return orderService.setStatus(id = id, status = status)
    }

    @PostMapping("/{id}/set-price")
    fun book(@PathVariable id: Int, @RequestBody(required = false) price: Int?) {
        if (price == 0) throw RuntimeException("Бесплатно не раздаём")
        return orderService.setPrice(id = id, price = price)
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Int) {
        orderService.delete(id)
    }

}