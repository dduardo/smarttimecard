package com.santiago.smarttimecard.services

import com.santiago.smarttimecard.documents.Company

interface CompanyService {

    fun findByCnpj(cnpj: String): Company?

    fun persist(company: Company): Company
}