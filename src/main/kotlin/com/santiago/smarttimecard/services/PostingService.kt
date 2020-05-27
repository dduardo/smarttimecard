package com.santiago.smarttimecard.services

import com.santiago.smarttimecard.documents.Posting
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface PostingService {

    fun findById(id: String): Posting?

    fun findByIdEmployee(idEmployee: String, pageRequest: PageRequest): Page<Posting>

    fun persist(posting: Posting): Posting

    fun remove(id: String)
}