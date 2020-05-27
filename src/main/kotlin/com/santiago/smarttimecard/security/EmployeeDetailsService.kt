package com.santiago.smarttimecard.security

import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.services.EmployeeService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class EmployeeDetailsService(val employeeService: EmployeeService) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val employee: Employee? = employeeService.findByEmail(username)
            if (employee != null) {
                return MainEmployee(employee)
            }
        }

        throw UsernameNotFoundException(username)
    }
}
