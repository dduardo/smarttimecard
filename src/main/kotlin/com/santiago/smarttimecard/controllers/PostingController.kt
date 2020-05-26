package com.santiago.smarttimecard.controllers

import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.documents.Posting
import com.santiago.smarttimecard.dtos.PostingDto
import com.santiago.smarttimecard.enums.EnumType
import com.santiago.smarttimecard.response.Response
import com.santiago.smarttimecard.services.EmployeeService
import com.santiago.smarttimecard.services.PostingService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/api/posting")
class PostingController(val postingService: PostingService,
                        val employeeService: EmployeeService) {

    private val dateFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss")

    @Value("\${pagination.qty_per_page}")
    val qtyPerPage: Int = 15

    @GetMapping("/employee/{idEmployee}")
    fun listByIdemployee(@PathVariable("idEmployee") idEmployee: String,
                               @RequestParam(value = "pag", defaultValue = "0") pag: Int,
                               @RequestParam(value = "ord", defaultValue = "id") ord: String,
                               @RequestParam(value = "dir", defaultValue = "DESC") dir: String):
            ResponseEntity<Response<Page<PostingDto>>> {

        val response: Response<Page<PostingDto>> = Response<Page<PostingDto>>()

        val pageRequest: PageRequest = PageRequest.of(pag, qtyPerPage, Sort.Direction.valueOf(dir), ord)
        val postings: Page<Posting> = postingService.findByIdEmployee(idEmployee, pageRequest)
        val postingDto: Page<PostingDto> = postings.map { posting -> postingDtoConverter(posting) }

        response.data = postingDto
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun lisById(@PathVariable("id") id: String): ResponseEntity<Response<PostingDto>> {
        val response: Response<PostingDto> = Response<PostingDto>()
        val posting: Posting? = postingService.findById(id)

        if (posting == null) {
            response.errors.add("Lançamento não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = postingDtoConverter(posting)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun Add(@Valid @RequestBody postingDto: PostingDto,
            result: BindingResult): ResponseEntity<Response<PostingDto>> {
        val response: Response<PostingDto> = Response<PostingDto>()
        validateEmployee(postingDto, result)

        if (result.hasErrors()) {
            //TODO for (erro in result.allErrors) response.erros.add(erro.defaultMessage)
//            for (error in result.allErrors) error.defaultMessage?.let { response.errors.add(it) }
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.errors.add(it) } }
            return ResponseEntity.badRequest().body(response)
        }

        val posting: Posting = convertDtoToPosting(postingDto, result)
        postingService.persist(posting)

        response.data = postingDtoConverter(posting)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @Valid @RequestBody postingDto: PostingDto,
               result: BindingResult): ResponseEntity<Response<PostingDto>> {

        val response: Response<PostingDto> = Response<PostingDto>()
        validateEmployee(postingDto, result)

        postingDto.id = id
        val posting: Posting = convertDtoToPosting(postingDto, result)

        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.errors.add(it) } }
            return ResponseEntity.badRequest().body(response)
        }

        postingService.persist(posting)
        response.data = postingDtoConverter(posting)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping(value = ["/{id}"])
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Response<String>> {

        val response: Response<String> = Response<String>()
        val posting: Posting? = postingService.findById(id)

        if (posting == null) {
            response.errors.add("Erro ao remover lançamento. Registro não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        postingService.remove(id)
        return ResponseEntity.ok(Response<String>())
    }

    private fun validateEmployee(postingDto: PostingDto, result: BindingResult) {
        if (postingDto.idEmployee == null) {
            result.addError(ObjectError("Employee", "Funcionário não informado"))
            return
        }

        val employee: Employee? = employeeService.findById(postingDto.idEmployee)
        if (employee == null) {
            result.addError(ObjectError("Employee", "Funcionário não encontrado. ID inexistente"));
        }
    }

    private fun postingDtoConverter(posting: Posting): PostingDto = PostingDto(dateFormat.format(posting.date),
            posting.type.toString(), posting.idEmployee, posting.description,
            posting.localization, posting.id)

    private fun convertDtoToPosting(postingDto: PostingDto, result: BindingResult): Posting {
        if (postingDto.id != null) {
            val posting: Posting? = postingService.findById(postingDto.id ?: "")
            if (posting == null) result.addError(ObjectError("Posting", "Posting not found."))
        }

        return Posting(dateFormat.parse(postingDto.date), EnumType.valueOf(postingDto.type ?: ""),
                postingDto.idEmployee ?: "", postingDto.description,
                postingDto.localization, postingDto.id)
    }
}
