package com.study.process.client

import com.study.process.dto.refill.BookRefillDto
import com.study.process.dto.refill.RefillDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "refill-service", url = "\${services.refill.url}")
interface RefillClient {

    @PostMapping("book")
    fun bookRefill(@RequestBody bookRefillDto: BookRefillDto): RefillDto

    @PostMapping("{id}/unbook")
    fun unbookRefill(@RequestParam id: Int)

}