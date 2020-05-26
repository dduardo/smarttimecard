package com.santiago.smarttimecard.repositories

import com.santiago.smarttimecard.documents.Company
import org.springframework.data.mongodb.repository.MongoRepository

interface CompanyRepository : MongoRepository<Company, String> {

    fun findByCnpj(cnpj: String): Company?
}