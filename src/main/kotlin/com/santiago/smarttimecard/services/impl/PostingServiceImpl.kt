package com.santiago.smarttimecard.services.impl

import com.santiago.smarttimecard.documents.Posting
import com.santiago.smarttimecard.repositories.PostingRepository
import com.santiago.smarttimecard.services.PostingService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostingServiceImpl(val postingRepository: PostingRepository) : PostingService {

    override fun findById(id: String): Posting? = postingRepository.findById(id).get()

    override fun findByIdEmployee(idEmployee: String, pageRequest: PageRequest): Page<Posting> =
            postingRepository.findByIdEmployee(idEmployee, pageRequest)

    override fun persist(posting: Posting): Posting = postingRepository.save(posting)

    override fun remove(id: String) = postingRepository.deleteById(id)
}