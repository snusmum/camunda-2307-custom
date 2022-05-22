package com.study.process.delegate

import com.study.common.RefillColor
import com.study.process.client.RefillClient
import com.study.process.common.*
import com.study.process.dto.refill.BookRefillDto
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.springframework.stereotype.Component

@Component
class ReserveRefillDelegate(private val refillClient: RefillClient): ErrorWrapDelegate() {

    override fun executeInternal(execution: DelegateExecution) {

        val bookRefillDto = BookRefillDto(
            orderId = execution.readInt(ORDER_ID_VAR_NAME),
            length = execution.readInt(LENGTH_VAR_NAME),
            color = execution.readEnumFromString<RefillColor>(COLOR_VAR_NAME),
        )

        val refill = refillClient.bookRefill(bookRefillDto)

        execution.setVariable(REFILL_ID_VAR_NAME, refill.id)
        execution.setVariable(REFILL_PRICE_VAR_NAME, refill.price)
    }

}