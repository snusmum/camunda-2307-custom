package com.study.process.client

import com.study.common.OrderStatus
import com.study.process.dto.order.CreateOrderDto
import com.study.process.dto.order.OrderDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "order-service", url = "\${services.order.url}")
interface OrderClient {

    @PostMapping("create")
    fun createOrder(@RequestBody createOrderDto: CreateOrderDto): OrderDto

    @GetMapping("{id}")
    fun getOrder(@RequestParam id: Int): OrderDto

    @PostMapping("{id}/set-price")
    fun setPrice(@RequestParam id: Int, @RequestBody price: Int)

    @PostMapping("{id}/set-price")
    fun removePrice(@RequestParam id: Int)

    @GetMapping("{id}/set-status")
    fun setStatus(@RequestParam id: Int, @RequestBody status: OrderStatus)

}