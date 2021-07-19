package com.instamojo.android.network

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.instamojo.android.Instamojo
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class ApiResponse<T> {
    var error: String? = ""
        private set
    var response: T? = null
        private set
    private var code: Int
    var errorMessage: String? = null
        private set
    private val handler = Handler(Looper.getMainLooper())

    constructor(response: Response<T>) {
        this.code = response.code()
        if (response.isSuccessful) {
            this.response = response.body()
        } else {
            var message = StringBuilder()
            try {
                if (response.errorBody() != null) {
                    error = response.errorBody()!!.string()
                    if (error != null && error!!.contains("slider_image3")) {
                        val errorsJson = JSONObject(error).getJSONArray("slider_image3")
                        for (i in 0 until errorsJson.length()) {
                            message.append(errorsJson.getString(i))
                            message.append(" ")
                        }
                    } else {
                        val errorsJson = JSONObject(error).getJSONArray("errors")
                        for (i in 0 until errorsJson.length()) {
                            message.append(errorsJson.getString(i))
                            message.append(" ")
                        }
                    }
                }
            } catch (ignored: Exception) {
                try {
                    val errorsJson = JSONObject(error)["errors"] as String
                    message.append(errorsJson)
                } catch (ignored1: Exception) {
                    message.append(parseError(error))
                }
            }
            if (message.length == 0) {
                message = StringBuilder("Something went wrong")
            }
            errorMessage = message.toString()
        }
    }

    private fun parseError(error: String?): String {
        return ""
    }

    constructor(throwable: Throwable?) {
        code = 500
        if (throwable is HttpException) {
            errorMessage = "Something went wrong"
        } else if (throwable is IOException) {
            errorMessage = "No Internet"
        } else {
            errorMessage = "Something went wrong"
        }
    }

    val isSuccess: Boolean
        get() = code >= 200 && code < 300

    fun response(): T? {
        return response
    }

    private fun getErrorMessage(responseBody: ResponseBody): String? {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.message
        }
    }
}