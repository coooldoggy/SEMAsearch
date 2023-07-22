package com.coooldoggy.semasearch.base

sealed class ResultData<out T : Any> {

    data class Success<out T : Any>(val data: T?) : ResultData<T>()

    data class Error(
        val code: String? = null,
        private val message: String? = null,
        val type: SemaResultCode = SemaResultCode.NONE,
    ) : ResultData<Nothing>() {
        fun getMessage(): String {
            message?.let {
                return message
            }
            return type.message
        }
    }
}
