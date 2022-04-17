package com.study.refill.service

import com.study.common.RefillColor
import com.study.refill.model.Refill
import com.study.refill.repository.RefillRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class RefillService(private val refillRepository: RefillRepository) {

    @Transactional
    fun create(length: Int, color: RefillColor, price: Int): Refill {
        return refillRepository.save(Refill(length = length, color = color, price = price))
    }

    @Transactional(readOnly = true)
    fun findAll(): MutableIterable<Refill> {
        return refillRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findByCharacteristicsNotBooked(length: Int, color: RefillColor): Refill {
        return refillRepository.findFirstByLengthAndColorAndOrderIdIsNull(length = length, color = color)
            ?: let { throw RuntimeException("стержень не найден") }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun book(length: Int, color: RefillColor, orderId: Int): Refill {
        val refill = findByCharacteristicsNotBooked(length = length, color = color)
        refill.orderId = orderId
        return refillRepository.save(refill)
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun unbook(id: Int) {
        refillRepository.findByIdOrNull(id)
            ?.let { b -> b.orderId = null; b; }
            ?.let { b -> refillRepository.save(b) }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Int) {
        refillRepository.deleteById(id)
    }


}