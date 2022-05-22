package com.study.process.delegate

import com.study.process.client.OrderClient
import com.study.process.common.BODY_PRICE_VAR_NAME
import com.study.process.common.ORDER_ID_VAR_NAME
import com.study.process.common.REFILL_PRICE_VAR_NAME
import com.study.process.common.readInt
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.springframework.stereotype.Component

@Component
class SetOrderPriceDelegate(private val orderClient: OrderClient): ErrorWrapDelegate() {

    override fun executeInternal(execution: DelegateExecution) {
        val orderId = execution.readInt(ORDER_ID_VAR_NAME)
        val bodyPrice = execution.readInt(BODY_PRICE_VAR_NAME)
        val refillPrice = execution.readInt(REFILL_PRICE_VAR_NAME)

        orderClient.setPrice(orderId, bodyPrice + refillPrice)
    }

}