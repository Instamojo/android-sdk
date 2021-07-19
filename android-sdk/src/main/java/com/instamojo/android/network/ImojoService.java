package com.instamojo.android.network;

import androidx.lifecycle.LiveData;

import com.instamojo.android.models.CardPaymentResponse;
import com.instamojo.android.models.GatewayOrder;
import com.instamojo.android.models.UPIStatusResponse;
import com.instamojo.android.models.UPISubmissionResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ImojoService {

    @GET("v2/gateway/orders/{orderID}/checkout-options/")
    LiveData<ApiResponse<GatewayOrder>> getPaymentOptions(@Path("orderID") String orderID);

    @FormUrlEncoded
    @POST
    LiveData<ApiResponse<CardPaymentResponse>> collectCardPayment(@Url String url, @FieldMap Map<String, String> cardPaymentRequest);

    @FormUrlEncoded
    @POST
    LiveData<ApiResponse<UPISubmissionResponse>> collectUPIPayment(@Url String url, @Field("virtual_address") String upiID);

    @GET
    LiveData<ApiResponse<UPIStatusResponse>> getUPIStatus(@Url String url);

}
