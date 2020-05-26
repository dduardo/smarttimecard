package com.santiago.smarttimecard.dtos

data class CompanyDto(
        val companyName: String,
        val cnpj: String,
        val id: String? = null
)