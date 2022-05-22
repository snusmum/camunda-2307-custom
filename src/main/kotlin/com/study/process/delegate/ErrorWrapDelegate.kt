package com.study.process.delegate

import com.study.process.common.ORDER_BPMN_ERROR_CODE
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate

abstract class ErrorWrapDelegate: JavaDelegate {

    abstract fun executeInternal(execution: DelegateExecution)

    override fun execute(execution: DelegateExecution) {
        try {
            executeInternal(execution)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw BpmnError(ORDER_BPMN_ERROR_CODE, ex)
        }
    }
}