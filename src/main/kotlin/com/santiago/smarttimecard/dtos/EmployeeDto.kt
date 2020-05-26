package com.santiago.smarttimecard.dtos

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class EmployeeDto(
        @get:NotEmpty(message = "Nome não pode ser vazio.")
        @get:Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
        val name: String = "",

        @get:NotEmpty(message = "Email não pode ser vazio.")
        @get:Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
        @get:Email(message="Email inválido.")
        val email: String = "",

        val password: String? = null,
        val valueOfHourWorked: String? = null,
        val numberOfHoursWorkedInTheDay: String? = null,
        val numberOfHoursInMeal: String? = null,
        val id: String? = null
)
