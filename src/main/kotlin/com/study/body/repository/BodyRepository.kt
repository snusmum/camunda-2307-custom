package com.study.body.repository

import com.study.body.model.Body
import org.springframework.data.repository.CrudRepository

interface BodyRepository : CrudRepository<Body, Int> {

    fun findFirstByLengthAndDiameterAndOrderIdIsNull(length: Int, diameter: Int): Body?

}
