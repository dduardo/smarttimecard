package com.santiago.smarttimecard.documents

import com.santiago.smarttimecard.enums.EnumType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Posting(
        @Id val id: String? = null,
        val date: Date,
        val type: EnumType,
        val idEmployee: String,
        val description: String? = "",
        val localization: String? = ""
)