package com.santiago.smarttimecard.controllers

import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.dtos.EmployeeDto
import com.santiago.smarttimecard.response.Response
import com.santiago.smarttimecard.services.EmployeeService
import com.santiago.smarttimecard.utils.PasswordUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/employee")
class EmployeeController(val employeeService: EmployeeService) {

    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: String, @Valid @RequestBody employeeDto: EmployeeDto,
                  result: BindingResult): ResponseEntity<Response<EmployeeDto>> {

        val response: Response<EmployeeDto> = Response<EmployeeDto>()
        val employee: Employee? = employeeService.findById(id)

        if (employee == null) {
            result.addError(ObjectError("Employee", "Employee not found."))
        }

        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.errors.add(it) } }
            return ResponseEntity.badRequest().body(response)
        }

        val funcUpdate: Employee = atualizarDadosFuncionario(employee!!, employeeDto)
        employeeService.persist(funcUpdate)
        response.data = converterFuncionarioDto(funcUpdate)

        return ResponseEntity.ok(response)
    }

    private fun atualizarDadosFuncionario(employee: Employee,
                                          employeeDto: EmployeeDto): Employee {
        var password: String
        if (employeeDto.password == null) {
            password = employee.password
        } else {
            password = PasswordUtils().generateBCript(employeeDto.password)
        }

        return Employee(employeeDto.name, employee.email, password,
                employee.cpf, employee.perfil, employee.companyID,
                employeeDto.valueOfHourWorked?.toDouble(),
                employeeDto.numberOfHoursWorkedInTheDay?.toFloat(),
                employeeDto.numberOfHoursInMeal?.toFloat(),
                employee.id)
    }

    private fun converterFuncionarioDto(employee: Employee): EmployeeDto =
            EmployeeDto(employee.name, employee.email, "",
                    employee.valueOfHourWorked.toString(), employee.numberOfHoursWorkedInTheDay.toString(),
                    employee.numberOfHoursInMeal.toString(), employee.id)

}
