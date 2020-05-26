package com.santiago.smarttimecard.dtos

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class RegisterPFDto (

        @get:NotEmpty(message = "Nome não pode ser vazio.")
        @get:Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
        val name: String = "",

        @get:NotEmpty(message = "Email não pode ser vazio.")
        @get:Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
        @get:Email(message="Email inválido.")
        val email: String = "",

        @get:NotEmpty(message = "Senha não pode ser vazia.")
        val password: String = "",

        @get:NotEmpty(message = "CPF não pode ser vazio.")
        @get:CPF(message="CPF inválido")
        val cpf: String = "",

        @get:NotEmpty(message = "CNPJ não pode ser vazio.")
        @get:CNPJ(message="CNPJ inválido.")
        val cnpj: String = "",

        val companyID: String? = "",
        val valueOfHourWorked: String? = null,
        val numberOfHoursWorkedInTheDay: String? = null,
        val numberOfHoursInMeal: String? = null,
        val id: String? = null
)
