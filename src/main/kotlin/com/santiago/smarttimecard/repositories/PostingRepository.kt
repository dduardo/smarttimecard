package com.santiago.smarttimecard.repositories

import com.santiago.smarttimecard.documents.Posting
import org.springframework.data.mongodb.repository.MongoRepository
import sun.jvm.hotspot.debugger.Page
import java.awt.print.Pageable

interface PostingRepository : MongoRepository<Posting, String> {

    fun findByIdEmployee(idEmployee: String, pageable: Pageable): Page<Posting>
}