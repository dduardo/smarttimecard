package com.santiago.smarttimecard.controllers

import com.santiago.smarttimecard.documents.Company
import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.dtos.RegisterPFDto
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
@RequestMapping("/api/register-pf")
class RegisterPFController(val companyService: CompanyService,
                           val employeeService: EmployeeService) {

    @PostMapping
    fun register(@Valid @RequestBody registerPFDto: RegisterPFDto,
                 result: BindingResult): ResponseEntity<Response<RegisterPFDto>> {
        val response: Response<RegisterPFDto> = Response()

        val company: Company? = companyService.findByCnpj(registerPFDto.cnpj)
        validateExistingData(registerPFDto, company, result)

        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.errors.add(it) } }
            return ResponseEntity.badRequest().body(response)
        }

        println("registerPFDto OLHA AQUII: " + registerPFDto.id)
        println("registerPFDto OLHA AQUII: " + registerPFDto.name)
        println("registerPFDto OLHA AQUII: " + registerPFDto.email)
        println("registerPFDto OLHA AQUII: " + registerPFDto.companyID)
        println("registerPFDto OLHA AQUII: " + registerPFDto.cnpj)
        println("registerPFDto OLHA AQUII: " + registerPFDto.password)
        println("registerPFDto OLHA AQUII: " + registerPFDto.numberOfHoursInMeal)
        println("registerPFDto OLHA AQUII: " + registerPFDto.numberOfHoursWorkedInTheDay)
        println("registerPFDto OLHA AQUII: " + registerPFDto.valueOfHourWorked)

        var employee: Employee = convertDtoToEmployee(registerPFDto, company!!)
        println("OLHA AQUII: " + employee.id)
        println("OLHA AQUII: " + employee.name)
        println("OLHA AQUII: " + employee.companyID)
        println("OLHA AQUII: " + employee.email)
        println("OLHA AQUII: " + employee.perfil.name)
        println("OLHA AQUII: " + employee.numberOfHoursInMeal)
        println("OLHA AQUII: " + employee.numberOfHoursWorkedInTheDay)
        println("OLHA AQUII: " + employee.valueOfHourWorked)
        println("OLHA AQUII: " + company.cnpj)
        println("OLHA AQUII: " + company.companyName)
        println("OLHA AQUII: " + company.id)

        employee = employeeService.persist(employee)

        println("OLHA AQUII: " + employee.id)
        println("OLHA AQUII: " + employee.name)
        println("OLHA AQUII: " + employee.companyID)
        println("OLHA AQUII: " + employee.email)
        println("OLHA AQUII: " + employee.perfil.name)
        println("OLHA AQUII: " + employee.numberOfHoursInMeal)
        println("OLHA AQUII: " + employee.numberOfHoursWorkedInTheDay)
        println("OLHA AQUII: " + employee.valueOfHourWorked)
        println("OLHA AQUII: " + company.cnpj)
        println("OLHA AQUII: " + company.companyName)
        println("OLHA AQUII: " + company.id)

        response.data = convertRegisterPFDto(employee, company)

        return ResponseEntity.ok(response)
    }

    private fun validateExistingData(registerPFDto: RegisterPFDto, company: Company?,
                                     result: BindingResult) {
        if (company == null) {
            result.addError(ObjectError("company", "Company not registered."))
        }

        val employeeCPF: Employee? = employeeService.findByCpf(registerPFDto.cpf)
        if (employeeCPF != null) {
            result.addError(ObjectError("Employee", "CPF already existing."))
        }

        val employeeEmail: Employee? = employeeService.findByEmail(registerPFDto.email)
        if (employeeEmail != null) {
            result.addError(ObjectError("Employee", "Email already existing."))
        }
    }

    private fun convertDtoToEmployee(registerPFDto: RegisterPFDto, company: Company) =
            Employee(registerPFDto.name, registerPFDto.email,
                    PasswordUtils().generateBCript(registerPFDto.password), registerPFDto.cpf,
                    EnumPerfil.ROLE_USER, company.id.toString(),
                    registerPFDto.valueOfHourWorked?.toDouble(), registerPFDto.numberOfHoursWorkedInTheDay?.toFloat(),
                    registerPFDto.numberOfHoursInMeal?.toFloat(), registerPFDto.id)


    private fun convertRegisterPFDto(employee: Employee, company: Company): RegisterPFDto =
            RegisterPFDto(employee.name, employee.email, "", employee.cpf,
                    company.cnpj, company.id.toString(), employee.valueOfHourWorked.toString(),
                    employee.numberOfHoursWorkedInTheDay.toString(),
                    employee.numberOfHoursInMeal.toString(),
                    employee.id)
}
