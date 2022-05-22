package com.study.process.bpm

import com.study.common.OrderStatus
import com.study.common.OrderStatus.CREATED
import com.study.common.RefillColor
import com.study.process.client.BodyClient
import com.study.process.client.OrderClient
import com.study.process.client.RefillClient
import com.study.process.common.*
import com.study.process.delegate.*
import com.study.process.dto.body.BodyDto
import com.study.process.dto.order.CreateOrderDto
import com.study.process.dto.order.OrderDto
import com.study.process.dto.refill.RefillDto
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*
import org.camunda.bpm.engine.test.mock.Mocks
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@Deployment(resources = ["bpmn/2307-custom.bpmn"])
class ProcessTest {

    companion object {
        @RegisterExtension
        @JvmField
        val extension: ProcessEngineExtension = ProcessEngineExtension.builder()
            .useProcessEngine(StandaloneInMemProcessEngineConfiguration().buildProcessEngine())
            .build()
    }

    private val runtimeService = extension.processEngine.runtimeService

    private val orderClient: OrderClient = mock()
    private val bodyClient: BodyClient = mock()
    private val refillClient: RefillClient = mock()

    private val length = 13
    private val diameter = 1
    private val color = RefillColor.BLUE

    private val orderId = 2
    private val bodyId = 6
    private val bodyPrice = 25
    private val refillId = 3
    private val refillPrice = 17

    private val order: OrderDto = OrderDto(
        id = orderId,
        length = length,
        diameter = diameter,
        color = color,
        status = CREATED
    )
    private val body: BodyDto = BodyDto(
        id = bodyId,
        price = bodyPrice,
    )
    private val refill: RefillDto = RefillDto(
        id = refillId,
        price = refillPrice,
    )

    @BeforeEach
    fun setup() {
        Mocks.register("createOrderDelegate", CreateOrderDelegate(orderClient))
        Mocks.register("getOrderDelegate", GetOrderDelegate(orderClient))
        Mocks.register("setOrderPriceDelegate", SetOrderPriceDelegate(orderClient))
        Mocks.register("reserveBodyDelegate", ReserveBodyDelegate(bodyClient))
        Mocks.register("reserveRefillDelegate", ReserveRefillDelegate(refillClient))
        Mocks.register("setOrderStatusDelegate", SetOrderStatusDelegate(orderClient))

        whenever(orderClient.getOrder(orderId)).thenReturn(order)
        whenever(bodyClient.bookBody(any())).thenReturn(body)
        whenever(refillClient.bookRefill(any())).thenReturn(refill)
    }

    @AfterEach
    fun teardown() {
        Mocks.reset()
    }

    @Test
    fun `green way for new process`() {
        // Arrange
        whenever(orderClient.createOrder(CreateOrderDto(length, diameter, color))).thenReturn(order)

        // Act #1
        val pi = startProcess(
            PEN_ORDER_PROCESS_START_NEW_MESSAGE,
            withVariables(
                LENGTH_VAR_NAME, length,
                DIAMETER_VAR_NAME, diameter,
                COLOR_VAR_NAME, color.name
            )
        )

        // Assert #1
        assertThat(pi).isWaitingAt("order_paid-message")
        verify(orderClient).setPrice(orderId, bodyPrice + refillPrice)

        // Act #2
        runtimeService.correlateMessage(ORDER_PAID_MESSAGE)

        // Assert #2
        assertThat(pi).isWaitingAt("assemble_order-service_task")

        // Act #3
        complete(externalTask("assemble_order-service_task"))

        // Assert #4
        assertThat(pi).isEnded
        verify(orderClient).setStatus(orderId, OrderStatus.COMPLETED)
    }

    @Test
    fun `green way for old process`() {
        // Arrange
        whenever(orderClient.getOrder(orderId)).thenReturn(order)

        // Act #1
        val pi = startProcess(PEN_ORDER_PROCESS_START_OLD_MESSAGE, withVariables(ORDER_ID_VAR_NAME, orderId))

        // Assert #1
        assertThat(pi).isWaitingAt("order_paid-message")
        verify(orderClient).setPrice(orderId, bodyPrice + refillPrice)

        // Act #2
        runtimeService.correlateMessage(ORDER_PAID_MESSAGE)

        // Assert #2
        assertThat(pi).isWaitingAt("assemble_order-service_task")

        // Act #3
        complete(externalTask("assemble_order-service_task"))

        // Assert #4
        assertThat(pi).isEnded
        verify(orderClient).setStatus(orderId, OrderStatus.COMPLETED)
    }

    private fun startProcess(message: String, variables: MutableMap<String, Any>): ProcessInstance {
        val pi = runtimeService.startProcessInstanceByMessage(message, variables)
        assertThat(pi).isNotNull
        return pi
    }

}