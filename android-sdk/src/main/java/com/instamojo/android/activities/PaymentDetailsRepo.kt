package com.instamojo.android.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.instamojo.android.models.GatewayOrder
import com.instamojo.android.network.ImojoService
import com.instamojo.android.network.Resource

class PaymentDetailsRepo(val imojoService: ImojoService) {

    fun getOrderDetails(orderId: String): LiveData<Resource<GatewayOrder>>  {
        val mediatorLiveData = MediatorLiveData<Resource<GatewayOrder>>()
        mediatorLiveData.value = Resource.loading()
        val liveData = imojoService.getPaymentOptions(orderId)
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