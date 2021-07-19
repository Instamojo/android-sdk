package com.instamojo.android.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.instamojo.android.models.CardPaymentResponse
import com.instamojo.android.models.UPIStatusResponse
import com.instamojo.android.models.UPISubmissionResponse
import com.instamojo.android.network.ImojoService
import com.instamojo.android.network.Resource

class UPIRepo(val imojoService: ImojoService) {

    fun collectUPIPayment(submissionUrl: String,vpa : String): LiveData<Resource<UPISubmissionResponse>> {
        val mediatorLiveData = MediatorLiveData<Resource<UPISubmissionResponse>>()
        mediatorLiveData.value = Resource.loading()
        val liveData = imojoService.collectUPIPayment(submissionUrl,vpa)
        mediatorLiveData.addSource(liveData) {
            if (it.isSuccess && it.response() != null) {
                mediatorLiveData.value = Resource.success(it.response())
            }else{
                mediatorLiveData.value = Resource.error(it.errorMessage,null)
            }
        }
        return mediatorLiveData
    }

    fun getUPIStatus(submissionUrl: String): LiveData<Resource<UPIStatusResponse>> {
        val mediatorLiveData = MediatorLiveData<Resource<UPIStatusResponse>>()
        mediatorLiveData.value = Resource.loading()
        val liveData = imojoService.getUPIStatus(submissionUrl)
        mediatorLiveData.addSource(liveData) {
            if (it.isSuccess && it.response() != null) {
                mediatorLiveData.value = Resource.success(it.response())
            }else{
                mediatorLiveData.value = Resource.error(it.errorMessage,null)
            }
        }
        return mediatorLiveData
    }


}