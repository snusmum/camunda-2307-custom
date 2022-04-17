package com.study.process.delegate

import com.study.process.client.OrderClient
import com.study.process.common.*
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class GetOrderDelegate(private val orderClient: OrderClient): JavaDelegate {
    override fun execute(execution: DelegateExecution) {

        val order = orderClient.getOrder(execution.readInt(ORDER_ID_VAR_NAME))

        execution.setVariable(LENGTH_VAR_NAME, order.length)
        execution.setVariable(DIAMETER_VAR_NAME, order.diameter)
        execution.setVariable(COLOR_VAR_NAME, order.color?.name)
    }
}