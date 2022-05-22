package com.study.process.client

import com.study.process.dto.body.BodyDto
import com.study.process.dto.body.BookBodyDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "body-service", url = "\${services.body.url}")
interface BodyClient {

    @PostMapping("book")
    fun bookBody(@RequestBody bookBodyDto: BookBodyDto): BodyDto

    @PostMapping("{id}/unbook")
    fun unbookBody(@RequestParam id: Int)

}