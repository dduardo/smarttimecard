package com.santiago.smarttimecard.services

import com.santiago.smarttimecard.documents.Employee

interface EmployeeService {

    fun persist(employee: Employee): Employee

    fun findByCpf(cpf: String): Employee?

    fun findByEmail(email: String): Employee?

    fun findById(id: String): Employee?
}