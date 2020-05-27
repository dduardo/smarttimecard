package com.santiago.smarttimecard.services

import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.enums.EnumPerfil
import com.santiago.smarttimecard.repositories.EmployeeRepository
import com.santiago.smarttimecard.utils.PasswordUtils
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureDataMongo
class EmployeeServiceTest {

    @MockBean
    private val employeeRepository: EmployeeRepository? = null

    @Autowired
    private val employeeService: EmployeeService? = null

    private val email: String = "email@email.com"
    private val cpf: String = "34234855948"
    private val id: String = "1"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(employeeRepository?.save(Mockito.any(Employee::class.java)))
                .willReturn(employee())
        BDDMockito.given(employeeRepository?.findById(id)).willReturn(Optional.of(employee()))
        BDDMockito.given(employeeRepository?.findByEmail(email)).willReturn(employee())
        BDDMockito.given(employeeRepository?.findByCpf(cpf)).willReturn(employee())
    }

    @Test
    fun testPersistirFuncionario() {
        val employee: Employee? = this.employeeService?.persist(employee())
        Assertions.assertNotNull(employee)
    }

    @Test
    fun testBuscarFuncionarioPorId() {
        val employee: Employee? = this.employeeService?.findById(id)
        Assertions.assertNotNull(employee)
    }

    @Test
    fun testBuscarFuncionarioPorEmail() {
        val employee: Employee? = this.employeeService?.findByEmail(email)
        Assertions.assertNotNull(employee)
    }

    @Test
    fun testBuscarFuncionarioPorCpf() {
        val employee: Employee? = this.employeeService?.findByCpf(cpf)
        Assertions.assertNotNull(employee)
    }

    private fun employee(): Employee = Employee("Name",  email, PasswordUtils().generateBCript("123456"),
            cpf, EnumPerfil.ROLE_USER, "1", id = id)
}