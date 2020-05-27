package com.santiago.smarttimecard.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.santiago.smarttimecard.documents.Company
import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.dtos.RegisterPFDto
import com.santiago.smarttimecard.enums.EnumPerfil
import com.santiago.smarttimecard.services.CompanyService
import com.santiago.smarttimecard.services.EmployeeService
import com.santiago.smarttimecard.utils.PasswordUtils
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataMongo
class RegisterPFControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val companyService: CompanyService? = null

    @MockBean
    private val employeeService: EmployeeService? = null
}
