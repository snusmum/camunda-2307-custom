package com.study.refill.api

import com.study.common.RefillColor
import com.study.refill.api.mapper.RefillMapper
import com.study.refill.dto.refill.BookRefillDto
import com.study.refill.dto.refill.CreateRefillDto
import com.study.refill.dto.refill.RefillDto
import com.study.refill.service.RefillService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/refills")
class RefillController(
    private val refillService: RefillService,
    private val refillMapper: RefillMapper
) {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dto: CreateRefillDto): RefillDto {
        return refillService.create(
            length = dto.length ?: 0,
            color = dto.color ?: RefillColor.WRONG,
            price = dto.price ?: 0
        )
            .let { b -> refillMapper.toDto(b) }
    }

    @GetMapping("/list")
    fun getAll(): List<RefillDto> {
        return refillMapper.toListDto(refillService.findAll())
    }

    @PostMapping("/book")
    fun book(@RequestBody dto: BookRefillDto): RefillDto {
        if (dto.color == RefillColor.WRONG) throw RuntimeException("Неодходящий цвет")
        return refillService.book(
            length = dto.length ?: 0,
            color = dto.color ?: RefillColor.WRONG,
            orderId = dto.orderId ?: 0
        )
            .let { b -> refillMapper.toDto(b) }
    }

    @PostMapping("/{id}/unbook")
    fun book(@PathVariable id: Int) {
        refillService.unbook(id)
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Int) {
        refillService.delete(id)
    }

}