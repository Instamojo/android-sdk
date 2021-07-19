package com.instamojo.android.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.instamojo.android.models.CardPaymentResponse
import com.instamojo.android.models.GatewayOrder
import com.instamojo.android.network.ImojoService
import com.instamojo.android.network.Resource

class CardsRepo(val imojoService: ImojoService) {

    fun checkout(submissionUrl: String,map: Map<String,String>): LiveData<Resource<CardPaymentResponse>> {
        val mediatorLiveData = MediatorLiveData<Resource<CardPaymentResponse>>()
        mediatorLiveData.value = Resource.loading()
        val liveData = imojoService.collectCardPayment(submissionUrl,map)
        mediatorLiveData.addSource(liveData) {
            if (it.isSuccess && it.response != null) {
                mediatorLiveData.value = Resource.success(it.response())
            }else{
                mediatorLiveData.value = Resource.error(it.errorMessage,null)
            }
        }
        return mediatorLiveData
    }


}