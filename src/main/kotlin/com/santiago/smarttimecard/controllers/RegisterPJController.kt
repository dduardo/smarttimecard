package com.santiago.smarttimecard.controllers

import com.santiago.smarttimecard.documents.Company
import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.dtos.RegisterPJDto
import com.santiago.smarttimecard.enums.EnumPerfil
import com.santiago.smarttimecard.response.Response
import com.santiago.smarttimecard.services.CompanyService
import com.santiago.smarttimecard.services.EmployeeService
import com.santiago.smarttimecard.utils.PasswordUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/register-pj")
class RegisterPJController(val companyService: CompanyService,
val employeeService: EmployeeService) {

    @PostMapping
    fun register(@Valid @RequestBody registerPJDto: RegisterPJDto,
                 result: BindingResult): ResponseEntity<Response<RegisterPJDto>> {
        val response: Response<RegisterPJDto> = Response<RegisterPJDto>()

        validateExistingData(registerPJDto, result)
        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.errors.add(it) } }
            return ResponseEntity.badRequest().body(response)
        }

        val company: Company = convertDtoToCompany(registerPJDto)
        companyService.persist(company)

        var employee: Employee = convertDtoToEmployee(registerPJDto, company)
        employee = employeeService.persist(employee)
        response.data = convertRegisterPJDto(employee, company)

        return ResponseEntity.ok(response)
    }

    private fun validateExistingData(registerPJDto: RegisterPJDto, result: BindingResult) {
        val company: Company? = companyService.findByCnpj(registerPJDto.cnpj)
        if (company != null) {
            result.addError(ObjectError("company", "CNPJ company already existing."))
        }

        val employeeCpf: Employee? = employeeService.findByCpf(registerPJDto.cpf)
        if (employeeCpf != null) {
            result.addError(ObjectError("employee", "CPF already existing."))
        }

        val employeeEmail: Employee? = employeeService.findByEmail(registerPJDto.email)
        if (employeeEmail != null) {
            result.addError(ObjectError("employee", "Email already existing."))
        }
    }

    private fun convertDtoToCompany(registerPJDto: RegisterPJDto): Company =
            Company(registerPJDto.companyName, registerPJDto.cnpj)

    private fun convertDtoToEmployee(registerPJDto: RegisterPJDto, company: Company) =
            Employee(registerPJDto.name, registerPJDto.email,
                    PasswordUtils().generateBCript(registerPJDto.password), registerPJDto.cpf,
                    EnumPerfil.ROLE_ADMIN, company.id.toString())

    private fun convertRegisterPJDto(employee: Employee, company: Company): RegisterPJDto =
            RegisterPJDto(employee.name, employee.email, "", employee.cpf,
                    company.cnpj, company.companyName, employee.id)
}
