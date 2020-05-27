package com.santiago.smarttimecard.services.impl

import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.repositories.EmployeeRepository
import com.santiago.smarttimecard.services.EmployeeService
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl(val employeeRepository: EmployeeRepository) : EmployeeService {

    override fun persist(employee: Employee): Employee = employeeRepository.save(employee)

    override fun findByCpf(cpf: String): Employee? = employeeRepository.findByCpf(cpf)

    override fun findByEmail(email: String): Employee? = employeeRepository.findByEmail(email)

    override fun findById(id: String): Employee? = employeeRepository.findById(id).get()
}