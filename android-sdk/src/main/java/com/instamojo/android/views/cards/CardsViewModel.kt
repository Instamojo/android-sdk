package com.instamojo.android.views.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.instamojo.android.models.CardPaymentResponse
import com.instamojo.android.network.Resource
import com.instamojo.android.repo.CardsRepo

class CardsViewModel(val cardsRepo: CardsRepo) : ViewModel() {

    fun checkout(submissionUrl: String, map: Map<String, String>): LiveData<Resource<CardPaymentResponse>> {
        return cardsRepo.checkout(submissionUrl, map)
    }

}