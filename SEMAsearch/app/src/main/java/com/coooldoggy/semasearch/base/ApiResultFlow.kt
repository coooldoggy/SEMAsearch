package com.coooldoggy.semasearch.base

import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import retrofit2.Response


inline fun <reified T : Any> apiResultFlow(call: () -> Response<String>): ResultData<T> {
    try {
        val json = Json {
            encodeDefaults = true
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val response = call.invoke()
        val jsonString = response.body() ?: "null"
        val isCorrectData = jsonString.contains("list_total_count")

        val jsonObject = JSONObject(jsonString)
        val codeObject = jsonObject.getJSONObject("RESULT")

        Log.d("!!!!", "$codeObject")

        return if (isCorrectData.not() && response.body() !is T) {
            val errorResult = codeObject.getString("CODE")
            ResultData.Error(
                type = (SemaResultCode.from(errorResult)),
            )
        } else {
            val data = json.decodeFromString<T>(jsonString)
            ResultData.Success(
                data = data,
            )
        }
    } catch (e: Exception) {
        Log.d("apiResultFlow", "${e.message}")
    }
    return ResultData.Error()
}
