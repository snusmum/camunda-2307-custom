package com.study.body.service

import com.study.body.model.Body
import com.study.body.repository.BodyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class BodyService(private val bodyRepository: BodyRepository) {

    @Transactional
    fun create(length: Int, diameter: Int, price: Int): Body {
        return bodyRepository.save(Body(length = length, diameter = diameter, price = price))
    }

    @Transactional(readOnly = true)
    fun findAll(): MutableIterable<Body> {
        return bodyRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findByCharacteristicsNotBooked(length: Int, diameter: Int): Body {
        return bodyRepository.findFirstByLengthAndDiameterAndOrderIdIsNull(length = length, diameter = diameter)
            ?: let { throw RuntimeException("корпус не найден") }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun book(length: Int, diameter: Int, orderId: Int): Body {
        val body = findByCharacteristicsNotBooked(length = length, diameter = diameter)
        body.orderId = orderId
        return bodyRepository.save(body)
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun unbook(id: Int) {
        bodyRepository.findByIdOrNull(id)
            ?.let { b -> b.orderId = null; b; }
            ?.let { b -> bodyRepository.save(b) }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Int) {
        bodyRepository.deleteById(id)
    }


}