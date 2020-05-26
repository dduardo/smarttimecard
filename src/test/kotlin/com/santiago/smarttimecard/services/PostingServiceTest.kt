package com.santiago.smarttimecard.services

import com.santiago.smarttimecard.documents.Posting
import com.santiago.smarttimecard.enums.EnumType
import com.santiago.smarttimecard.repositories.PostingRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureDataMongo
class PostingServiceTest {

    @MockBean
    private val postingRepository: PostingRepository? = null

    @Autowired
    private val postingService: PostingService? = null

    private val id: String = "1"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito
                .given<Page<Posting>>(postingRepository?.findByIdEmployee(id, PageRequest.of(0, 10)))
                .willReturn(PageImpl(ArrayList<Posting>()))
        BDDMockito.given(postingRepository?.findById("1")).willReturn(Optional.of(posting()))
        BDDMockito.given(postingRepository?.save(Mockito.any(Posting::class.java)))
                .willReturn(posting())
    }

    @Test
    fun testBuscarLancamentoPorFuncionarioId() {
        val lancamento: Page<Posting>? = postingService?.findByIdEmployee(id, PageRequest.of(0, 10))
        Assertions.assertNotNull(lancamento)
    }

    @Test
    fun testBuscarLancamentoPorId() {
        val lancamento: Posting? = postingService?.findById(id)
        Assertions.assertNotNull(lancamento)
    }

    @Test
    fun testPersistirLancamento() {
        val lancamento: Posting? = postingService?.persist(posting())
        Assertions.assertNotNull(lancamento)
    }

    private fun posting(): Posting = Posting(id, Date(), EnumType.START_WORK, "1", "Desccription PostingMock", "Localization")
}