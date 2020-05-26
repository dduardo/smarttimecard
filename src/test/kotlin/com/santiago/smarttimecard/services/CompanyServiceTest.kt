package com.santiago.smarttimecard.services

import com.santiago.smarttimecard.documents.Company
import com.santiago.smarttimecard.repositories.CompanyRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureDataMongo
class CompanyServiceTest {

    @MockBean
    private val companyRepository: CompanyRepository? = null

    @Autowired
    private val companyService: CompanyService? = null

    private val CNPJ = "51463645000100"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(companyRepository?.findByCnpj(CNPJ)).willReturn(company())
        BDDMockito.given(companyRepository?.save(company())).willReturn(company())
    }

    @Test
    fun testFindCompanyByCnpj() {
        val company: Company? = companyService?.findByCnpj(CNPJ)
        Assertions.assertNotNull(company)
    }

    @Test
    fun testPersistCompany() {
        val company: Company? = companyService?.persist(company())
        Assertions.assertNotNull(company)
    }

    private fun company(): Company = Company(companyName = "Company Name1", cnpj = CNPJ)
}