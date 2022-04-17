package com.study.process.delegate

import com.study.common.OrderStatus
import com.study.process.client.OrderClient
import com.study.process.common.ORDER_ID_VAR_NAME
import com.study.process.common.ORDER_STATUS_VAR_NAME
import com.study.process.common.readEnumFromString
import com.study.process.common.readInt
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class SetOrderStatusDelegate(private val orderClient: OrderClient): JavaDelegate {
    override fun execute(execution: DelegateExecution) {

        val orderId = execution.readInt(ORDER_ID_VAR_NAME)
        val orderStatus = execution.readEnumFromString<OrderStatus>(ORDER_STATUS_VAR_NAME)

        orderClient.setStatus(orderId, orderStatus)
    }
}