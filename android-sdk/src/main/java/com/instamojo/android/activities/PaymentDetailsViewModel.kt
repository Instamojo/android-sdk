package com.instamojo.android.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.instamojo.android.models.GatewayOrder
import com.instamojo.android.network.Resource

class PaymentDetailsViewModel(private val paymentDetailsRepo : PaymentDetailsRepo) : ViewModel() {

    fun getOrderDetails(orderId:String) : LiveData<Resource<GatewayOrder>>{
        return paymentDetailsRepo.getOrderDetails(orderId)
    }

}