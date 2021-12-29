package com.maheshmanseta.mvvm.ui.mahesh.repos

import com.google.gson.Gson
import com.maheshmanseta.mvvm.datamodels.responses.BeansResponseCard
import com.maheshmanseta.mvvm.retrofit.ApiClient
import com.maheshmanseta.mvvm.retrofit.ApiService
import com.maheshmanseta.mvvm.utils.AppPreferences
import com.maheshmanseta.mvvm.utils.AppUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object RepositoryMaheshDashboard {
    suspend fun getDataFromServer(
        token: String?, isConnected: Boolean
    ) = withContext(Dispatchers.Default) {
        if (isConnected) {
            val callApi: ApiService =
                ApiClient.getClient(AppUtils.getHeaders(token))!!.create(ApiService::class.java)
            callApi.getData()
        } else {
            getDataFromLocal()
        }
    }

    private fun getDataFromLocal(): BeansResponseCard {
        val gson = Gson()
        return gson.fromJson(AppPreferences.userData, BeansResponseCard::class.java)
    }
}

