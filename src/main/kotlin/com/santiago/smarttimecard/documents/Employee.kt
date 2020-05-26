package com.santiago.smarttimecard.documents

import com.santiago.smarttimecard.enums.EnumPerfil
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(
        val name: String,
        val email: String,
        val password: String,
        val cpf: String,
        val perfil: EnumPerfil,
        val companyID: String,
        val valueOfHourWorked: Double? = 0.0,
        val numberOfHoursWorkedInTheDay: Float? = 0.0f,
        val numberOfHoursInMeal: Float? = 0.0f,
        @Id val id: String? = null
)