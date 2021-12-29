package com.maheshmanseta.mvvm.retrofit

import com.maheshmanseta.mvvm.datamodels.responses.BeansResponseCard
import retrofit2.http.GET

interface ApiService {

    // Get Data.
    @GET("v3/2abb5b4e-b46b-4b0d-a7ba-a20eb394782a")
    suspend fun getData(): BeansResponseCard
}