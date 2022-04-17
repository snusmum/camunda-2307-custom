package com.study.body.api

import com.study.body.api.mapper.BodyMapper
import com.study.body.dto.BodyDto
import com.study.body.dto.BookBodyDto
import com.study.body.dto.CreateBookBodyDto
import com.study.body.service.BodyService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/bodies")
class BodyController(
    private val bodyService: BodyService,
    private val bodyMapper: BodyMapper
) {

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dto: CreateBookBodyDto): BodyDto {
        return bodyService.create(length = dto.length ?: 0, diameter = dto.diameter ?: 0, price = dto.price ?: 0)
            .let { b -> bodyMapper.toDto(b) }
    }

    @GetMapping("/list")
    fun getAll(): List<BodyDto> {
        return bodyMapper.toListDto(bodyService.findAll())
    }

    @PostMapping("/book")
    fun book(@RequestBody dto: BookBodyDto): BodyDto {
        if (dto.diameter == 0) throw RuntimeException("Неодходящий деаметр")
        return bodyService.book(length = dto.length ?: 0, diameter = dto.diameter ?: 0, orderId = dto.orderId ?: 0)
            .let { b -> bodyMapper.toDto(b) }
    }

    @PostMapping("/{id}/unbook")
    fun book(@PathVariable id: Int) {
         bodyService.unbook(id)
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Int) {
         bodyService.delete(id)
    }

}