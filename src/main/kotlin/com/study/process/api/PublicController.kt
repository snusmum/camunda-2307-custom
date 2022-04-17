package com.study.process.api

import com.study.process.common.*
import com.study.process.dto.order.CreateOrderDto
import com.study.process.service.CamundaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/processes")
class PublicController(private val camundaService: CamundaService) {

    @PostMapping("/start/new")
    fun startNewOrder(@RequestBody dto: CreateOrderDto) {
        camundaService.startProcess(
            PEN_ORDER_PROCESS_START_NEW_MESSAGE,
            mapOf(LENGTH_VAR_NAME to dto.length, DIAMETER_VAR_NAME to dto.diameter, COLOR_VAR_NAME to dto.color?.name)
        )
    }

    @PostMapping("/start/old/{id}")
    fun startOldOrder(@PathVariable id: Int) {
        camundaService.startProcess(
            PEN_ORDER_PROCESS_START_OLD_MESSAGE,
            mapOf(ORDER_ID_VAR_NAME to id)
        )
    }

    @PostMapping("/approve/{id}")
    fun approvePayment(@PathVariable id: Int) {
        camundaService.notifyProcess(PEN_ORDER_APPROVE_PAYMENT_MESSAGE, id)
    }

    @PostMapping("/cancel/{id}")
    fun cancelOrder(@PathVariable id: Int) {
        camundaService.notifyProcess(PEN_ORDER_CANCEL_MESSAGE, id)
    }

}