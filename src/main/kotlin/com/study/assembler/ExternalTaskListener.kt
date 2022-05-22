package com.study.assembler

import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ExternalTaskListener(@Value("\${services.external-task.url}") private val processUrl: String) {

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent) {
        startListening()
    }

    private fun startListening() {
        val client: ExternalTaskClient = ExternalTaskClient.create()
            .baseUrl(processUrl)
            .asyncResponseTimeout(1000)
            .build()

        client.subscribe("assembleOrderTopic").handler(Handler()).open()
    }

    class Handler: ExternalTaskHandler {
        override fun execute(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
            try {
                externalTaskService.complete(externalTask)

                val length = externalTask.getVariable<Int>("length")
                if (length == 21) throw RuntimeException("Не поместилось на сборочном столе")

                val orderId = externalTask.getVariable<Any>("order_id")
                println("Complete $orderId")
            } catch (e: Exception) {
                e.printStackTrace()
                externalTaskService.handleFailure(
                    externalTask,
                    e.message ?: "fail assemble",
                    e.stackTrace.toString(),
                    1,
                    0L
                )
            }
        }
    }

}