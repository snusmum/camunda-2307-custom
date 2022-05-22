package com.study.process.delegate

import com.study.process.client.BodyClient
import com.study.process.common.*
import com.study.process.dto.body.BookBodyDto
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.springframework.stereotype.Component

@Component
class ReserveBodyDelegate(private val bodyClient: BodyClient) : ErrorWrapDelegate() {
    
    override fun executeInternal(execution: DelegateExecution) {
        val bookBodyDto = BookBodyDto(
            orderId = execution.readInt(ORDER_ID_VAR_NAME),
            length = execution.readInt(LENGTH_VAR_NAME),
            diameter = execution.readInt(DIAMETER_VAR_NAME),
        )

        val body = bodyClient.bookBody(bookBodyDto)

        execution.setVariable(BODY_ID_VAR_NAME, body.id)
        execution.setVariable(BODY_PRICE_VAR_NAME, body.price)
    }
    
}