package com.santiago.smarttimecard.repositories

import com.santiago.smarttimecard.documents.Posting
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface PostingRepository : MongoRepository<Posting, String> {

    fun findByIdEmployee(idEmployee: String, pageable: Pageable): Page<Posting>
}
