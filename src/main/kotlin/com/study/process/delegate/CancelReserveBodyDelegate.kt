package com.study.process.delegate

import com.study.process.client.BodyClient
import com.study.process.common.BODY_ID_VAR_NAME
import com.study.process.common.readInt
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class CancelReserveBodyDelegate(private val bodyClient: BodyClient): JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        bodyClient.unbookBody(execution.readInt(BODY_ID_VAR_NAME))
    }
}