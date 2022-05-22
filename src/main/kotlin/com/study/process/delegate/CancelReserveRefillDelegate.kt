package com.study.process.delegate

import com.study.process.client.RefillClient
import com.study.process.common.REFILL_ID_VAR_NAME
import com.study.process.common.readInt
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class CancelReserveRefillDelegate(private val refillClient: RefillClient): JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        refillClient.unbookRefill(execution.readInt(REFILL_ID_VAR_NAME))
    }
}