package com.study.order.repository

import com.study.order.model.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<Order, Int> {
}
