package com.santiago.smarttimecard

import com.santiago.smarttimecard.documents.Company
import com.santiago.smarttimecard.documents.Employee
import com.santiago.smarttimecard.enums.EnumPerfil
import com.santiago.smarttimecard.repositories.CompanyRepository
import com.santiago.smarttimecard.repositories.EmployeeRepository
import com.santiago.smarttimecard.repositories.PostingRepository
import com.santiago.smarttimecard.utils.PasswordUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

//@SpringBootApplication
//class SmarttimecardApplication
@SpringBootApplication(exclude = arrayOf(SecurityAutoConfiguration::class))
class SmarttimecardApplication(val companyRepository: CompanyRepository,
							   val employeeRepository: EmployeeRepository,
							   val postingRepository: PostingRepository) : CommandLineRunner {
	override fun run(vararg args: String?) {
		companyRepository.deleteAll()
		employeeRepository.deleteAll()
		postingRepository.deleteAll()

		var company: Company = Company("Razao social 1", "123123123123")
		company = companyRepository.save(company)

		var admin = Employee( "name", "email@email.com", PasswordUtils().generateBCript("123123123"),
				"1231231232", EnumPerfil.ROLE_ADMIN, company.id!!)
		admin = employeeRepository.save(admin)

		var employee = Employee("name2", "email2@email.com", PasswordUtils().generateBCript("123123123"),
				"1231231232", EnumPerfil.ROLE_USER, company.id!!)
		employee = employeeRepository.save(employee)

		System.out.println("Company ID: " + company.id)
		System.out.println("Employee ADMIN ID: " + admin.id)
		System.out.println("Employee ADMIN IDCompany: " + admin.companyID)
		System.out.println("Employee ID: " + employee.id)
		System.out.println("Employee IDCompany: " + employee.companyID)
	}
}

fun main(args: Array<String>) {
	runApplication<SmarttimecardApplication>(*args)
}
