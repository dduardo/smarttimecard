package com.santiago.smarttimecard.dtos

import javax.validation.constraints.NotEmpty

data class PostingDto(
        val id: String? = null,

        @get:NotEmpty(message = "Data não pode ser vazia.")
        val date: String? = null,

        @get:NotEmpty(message = "Tipo não pode ser vazio.")
        val type: String? = null,
        val idEmployee: String? = null,
        val description: String? = null,
        val localization: String? = null
)