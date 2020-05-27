package com.santiago.smarttimecard.security

import com.santiago.smarttimecard.documents.Employee
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MainEmployee(val employee: Employee) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = mutableListOf<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(employee.perfil.toString()))
        return authorities
    }

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = employee.email

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = employee.password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}
