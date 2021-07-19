package com.instamojo.android.network

import androidx.lifecycle.LiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean


class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        val observableType = getParameterUpperBound(0, type as ParameterizedType)
        return LiveDataCallAdapter<Any>(getParameterUpperBound(0, observableType as ParameterizedType))
    }

    class LiveDataCallAdapter<R>(private val returnType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {
        override fun responseType(): Type {
            return returnType
        }

        override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
            return object : LiveData<ApiResponse<R>>() {
                private val isStarted = AtomicBoolean(false)
                override fun onActive() {
                    if (isStarted.compareAndSet(false, true)) {
                        call.enqueue(object : Callback<R> {
                            override fun onResponse(call: Call<R>, response: Response<R>) {
                                postValue(ApiResponse(response))
                            }

                            override fun onFailure(call: Call<R>, throwable: Throwable) {
                                postValue(ApiResponse<R>(throwable))
                            }
                        })
                    }
                }
            }
        }
    }
}