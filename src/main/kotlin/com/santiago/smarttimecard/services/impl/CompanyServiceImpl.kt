package com.santiago.smarttimecard.services.impl

import com.santiago.smarttimecard.documents.Company
import com.santiago.smarttimecard.repositories.CompanyRepository
import com.santiago.smarttimecard.services.CompanyService
import org.springframework.stereotype.Service

@Service
class CompanyServiceImpl(val companyRepository: CompanyRepository) : CompanyService {

    override fun findByCnpj(cnpj: String): Company? = companyRepository.findByCnpj(cnpj)

    override fun persist(company: Company): Company = companyRepository.save(company)
}