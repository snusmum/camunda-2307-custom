package com.study.order.service

import com.study.common.OrderStatus
import com.study.common.RefillColor
import com.study.order.model.Order
import com.study.order.repository.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(private val orderRepository: OrderRepository) {

    @Transactional
    fun create(length: Int, diameter: Int, color: RefillColor): Order {
        return orderRepository.save(
            Order(
                length = length,
                diameter = diameter,
                color = color,
                status = OrderStatus.CREATED
            )
        )
    }

    @Transactional(readOnly = true)
    fun get(id: Int): Order {
        return orderRepository.findByIdOrNull(id)?:let { throw RuntimeException("заказ не найден") }
    }

    @Transactional(readOnly = true)
    fun findAll(): MutableIterable<Order> {
        return orderRepository.findAll()
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun setPrice(id: Int, price: Int?) {
        orderRepository.findByIdOrNull(id)
            ?.let { b -> b.price = price; b; }
            ?.let { b -> orderRepository.save(b) }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun setStatus(id: Int, status: OrderStatus) {
        orderRepository.findByIdOrNull(id)
            ?.let { b -> b.status = status; b; }
            ?.let { b -> orderRepository.save(b) }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Int) {
        orderRepository.deleteById(id)
    }


}