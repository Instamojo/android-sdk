package com.instamojo.android.views.upi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.instamojo.android.helpers.mock
import com.instamojo.android.models.UPIStatusResponse
import com.instamojo.android.models.UPISubmissionResponse
import com.instamojo.android.network.Resource
import com.instamojo.android.repo.UPIRepo
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class UPIViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    val upiRepo = Mockito.mock(UPIRepo::class.java)
    var viewModel : UPIViewModel? = null

    val submissionUrl = "https://www.dummyurl.com"
    val vpa = "dummyvpa@okhdfcbank"

    @Before
    fun setUp(){
        viewModel = UPIViewModel(upiRepo)
    }

    @Test
    fun onNewUPIStatusRequest() {
        viewModel?.onNewUPIStatusRequest(submissionUrl)
        val observer = mock<Observer<Resource<UPIStatusResponse>>>()
        viewModel?.getUPIStatus()?.observeForever(observer)
        Mockito.verify(upiRepo,Mockito.times(1)).getUPIStatus(submissionUrl)
    }

    @Test
    fun onNewCollectPaymentRequest() {
        viewModel?.onNewCollectPaymentRequest(submissionUrl,vpa)
        val observer = mock<Observer<Resource<UPISubmissionResponse>>>()
        viewModel?.collectUPIPayment()?.observeForever(observer)
        Mockito.verify(upiRepo,Mockito.times(1)).collectUPIPayment(submissionUrl,vpa)
    }


}