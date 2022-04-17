package com.study.process.delegate

import com.study.process.client.OrderClient
import com.study.process.common.ORDER_ID_VAR_NAME
import com.study.process.common.readInt
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class ResetOrderDelegate(private val orderClient: OrderClient): JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        orderClient.removePrice(execution.readInt(ORDER_ID_VAR_NAME))
    }
}