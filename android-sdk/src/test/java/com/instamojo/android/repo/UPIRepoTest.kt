package com.instamojo.android.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.instamojo.android.helpers.mock
import com.instamojo.android.models.UPIStatusResponse
import com.instamojo.android.models.UPISubmissionResponse
import com.instamojo.android.network.ApiResponse
import com.instamojo.android.network.ImojoService
import com.instamojo.android.network.Resource
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.*


@RunWith(JUnit4::class)
class UPIRepoTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val imojoService = Mockito.mock(ImojoService::class.java)

    var upiRepo : UPIRepo? = null

    val submissionUrl = "dummy url"

    val upiId = "abc@okhdfcbank"

    val errorMessage = "error message"

    @Before
    fun setUp() {
        upiRepo = UPIRepo(imojoService)
    }

    @Test
    fun collectUPIPayment() {
       val observer = mock<Observer<Resource<UPISubmissionResponse>>>()
        val mediatorLiveData = MediatorLiveData<ApiResponse<UPISubmissionResponse>>()
        val submissionUrlResponse = Mockito.mock(UPISubmissionResponse::class.java)
        val apiResponse = mock<ApiResponse<UPISubmissionResponse>>()
        Mockito.`when`(apiResponse.isSuccess).thenReturn(true)
        Mockito.`when`(apiResponse.response()).thenReturn(submissionUrlResponse)
        Mockito.`when`(imojoService.collectUPIPayment(submissionUrl,upiId)).thenReturn(mediatorLiveData)

        upiRepo?.collectUPIPayment(submissionUrl,upiId)?.observeForever(observer)


        mediatorLiveData.postValue(apiResponse)
        Mockito.verify(observer).onChanged(Resource.success(submissionUrlResponse))

        Mockito.`when`(apiResponse.isSuccess).thenReturn(false)
        Mockito.`when`(apiResponse.response).thenReturn(null)
        Mockito.`when`(apiResponse.errorMessage).thenReturn(errorMessage)
        mediatorLiveData.postValue(apiResponse)

        Mockito.verify(observer).onChanged(Resource.error(errorMessage, null))
    }

    @Test
    fun getUPIStatus() {
        val observer = mock<Observer<Resource<UPIStatusResponse>>>()
        val mediatorLiveData = MediatorLiveData<ApiResponse<UPIStatusResponse>>()
        Mockito.`when`(imojoService.getUPIStatus(submissionUrl)).thenReturn(mediatorLiveData)
        val submissionUrlResponse = Mockito.mock(UPIStatusResponse::class.java)
        val apiResponse = mock<ApiResponse<UPIStatusResponse>>()
        Mockito.`when`(apiResponse.isSuccess).thenReturn(true)
        Mockito.`when`(apiResponse.response()).thenReturn(submissionUrlResponse)

        upiRepo?.getUPIStatus(submissionUrl)?.observeForever(observer)

        mediatorLiveData.value = apiResponse
        Mockito.verify(observer).onChanged(Resource.success(submissionUrlResponse))

        Mockito.`when`(apiResponse.isSuccess).thenReturn(false)
        Mockito.`when`(apiResponse.response()).thenReturn(null)
        Mockito.`when`(apiResponse.errorMessage).thenReturn(errorMessage)
        mediatorLiveData.value = apiResponse

        Mockito.verify(observer).onChanged(Resource.error(errorMessage, null))
    }

}