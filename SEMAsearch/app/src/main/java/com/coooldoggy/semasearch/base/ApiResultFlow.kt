package com.coooldoggy.semasearch.base

import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response

inline fun <reified T : Any> apiResultFlow(call: () -> Response<String>): ResultData<T> {
    try {
        val response = call.invoke()
        val jsonString = response.body() ?: "null"
        val isCorrectData = jsonString.contains("list_total_count")

        return if (isCorrectData.not()) {
            val jsonObject = JSONObject(jsonString)
            val codeObject = jsonObject.getJSONObject("RESULT")
            val errorResult = codeObject.getString("CODE")
            ResultData.Error(
                type = (SemaResultCode.from(errorResult)),
            )
        } else {
            val data = Gson().fromJson(jsonString, T::class.java)
            ResultData.Success(
                data = data,
            )
        }
    } catch (e: Exception) {
        Log.d("apiResultFlow", "${e.message}")
    }
    return ResultData.Error()
}
