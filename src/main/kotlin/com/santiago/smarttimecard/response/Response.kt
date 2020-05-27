package com.santiago.smarttimecard.response

/*Generic data class to use in response */
data class Response<T>(
        val errors: ArrayList<String> = arrayListOf(),
        var data: T? = null
)