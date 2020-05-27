package com.santiago.smarttimecard.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.documents.Posting
import com.santiago.smarttimecard.dtos.PostingDto
import com.santiago.smarttimecard.enums.EnumPerfil
import com.santiago.smarttimecard.enums.EnumType
import com.santiago.smarttimecard.services.EmployeeService
import com.santiago.smarttimecard.services.PostingService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataMongo
class PostingControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val postingService: PostingService ? = null

    @MockBean
    private val employeeService: EmployeeService? = null

    private val urlBase: String = "/api/posting/"
    private val iDEmployee: String = "1"
    private val iDPosting: String = "1"
    private val type: String = EnumType.START_WORK.name
    private val date: Date = Date()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testRegisterPosting() {
        val posting: Posting =
                postingDataMock()
        BDDMockito.given<Employee>(employeeService?.findById(iDEmployee))
                .willReturn(employeeMock())
        BDDMockito.given(postingService?.persist(postingDataMock()))
                .willReturn(posting)

        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(getJsonRequisitionPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.type").value(type))
                .andExpect(jsonPath("$.data.date").value(dateFormat.format(date)))
                .andExpect(jsonPath("$.data.idEmployee").value(iDEmployee))
                .andExpect(jsonPath("$.errors").isEmpty())
    }

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testRegistrationPostingEmployeeIdInvalid() {
        BDDMockito.given<Employee>(employeeService?.findById(iDEmployee))
                .willReturn(null)

        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(getJsonRequisitionPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Employee not found. Id not found."))
                .andExpect(jsonPath("$.data").isEmpty())
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = arrayOf("ADMIN"))
    @Throws(Exception::class)
    fun testRemovePosting() {
        BDDMockito.given<Posting>(postingService?.findById(iDPosting))
                .willReturn(postingDataMock())

        mvc!!.perform(MockMvcRequestBuilders.delete(urlBase + iDPosting)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
    }

    @Test
    @WithMockUser
    @Throws(Exception::class)
    fun testRemovePostingAccessDenied() {
        BDDMockito.given<Posting>(postingService?.findById(iDPosting))
                .willReturn(postingDataMock())

        mvc!!.perform(MockMvcRequestBuilders.delete(urlBase + iDPosting)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
    }

    @Throws(JsonProcessingException::class)
    private fun getJsonRequisitionPost(): String {
        val postingDto: PostingDto = PostingDto(
                dateFormat.format(date), type, iDEmployee,"Descrição",
                "1.234,4.234")
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(postingDto)
    }

    private fun postingDataMock(): Posting =
            Posting(date, EnumType.valueOf(type), iDEmployee,
            "Description", "1.243,4.345", iDPosting)

    private fun employeeMock(): Employee =
            Employee("Name", "email@email.com", PasswordUtils().generateBCript("123456"),
                    "23145699876", EnumPerfil.ROLE_USER, iDEmployee)
}
