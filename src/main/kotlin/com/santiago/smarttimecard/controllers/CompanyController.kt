package com.santiago.smarttimecard.controllers

import com.santiago.smarttimecard.documents.Company
import com.santiago.smarttimecard.dtos.CompanyDto
import com.santiago.smarttimecard.response.Response
import com.santiago.smarttimecard.services.CompanyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/company")
class CompanyController(val companyService: CompanyService) {

    @GetMapping("/cnpj/{cnpj}")
    fun buscarPorCnpj(@PathVariable("cnpj") cnpj: String): ResponseEntity<Response<CompanyDto>> {
        val response: Response<CompanyDto> = Response<CompanyDto>()
        val company: Company? = companyService.findByCnpj(cnpj)

        if (company == null) {
            response.errors.add("Company not found for CNPJ ${cnpj}")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = convertCompanyDto(company)
        return ResponseEntity.ok(response)
    }

    private fun convertCompanyDto(company: Company): CompanyDto =
            CompanyDto(company.companyName, company.cnpj, company.id)
}
