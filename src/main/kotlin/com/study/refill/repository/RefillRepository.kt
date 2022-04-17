package com.study.refill.repository

import com.study.common.RefillColor
import com.study.refill.model.Refill
import org.springframework.data.repository.CrudRepository

interface RefillRepository : CrudRepository<Refill, Int> {

    fun findFirstByLengthAndColorAndOrderIdIsNull(length: Int, color: RefillColor): Refill?

}
