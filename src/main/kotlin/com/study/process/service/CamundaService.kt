package com.study.process.service

import com.study.process.common.ORDER_ID_VAR_NAME
import org.camunda.bpm.engine.RuntimeService
import org.springframework.stereotype.Service

@Service
class CamundaService(private val runtimeService: RuntimeService) {

    fun startProcess(message: String, variables: Map<String, Any?>) {
        runtimeService.startProcessInstanceByMessage(message, variables)
    }

    fun notifyProcess(message: String, entityId: Int) {
        runtimeService.createMessageCorrelation(message)
            .processInstanceVariableEquals(ORDER_ID_VAR_NAME, entityId)
            .correlate()
    }

}