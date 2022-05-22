package com.study.process.delegate

import com.study.common.RefillColor
import com.study.process.client.OrderClient
import com.study.process.common.*
import com.study.process.dto.order.CreateOrderDto
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class CreateOrderDelegate(private val orderClient: OrderClient) : JavaDelegate {

    override fun execute(execution: DelegateExecution) {

        val createOrderDto = CreateOrderDto(
            length = execution.readInt(LENGTH_VAR_NAME),
            diameter = execution.readInt(DIAMETER_VAR_NAME),
            color = execution.readEnumFromString<RefillColor>(COLOR_VAR_NAME)
        )

        val order = orderClient.createOrder(createOrderDto)

        execution.setVariable(ORDER_ID_VAR_NAME, order.id)
    }
}