package com.maheshmanseta.mvvm.ui.mahesh.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.maheshmanseta.mvvm.retrofit.Resource
import com.maheshmanseta.mvvm.ui.mahesh.repos.RepositoryMaheshDashboard
import com.maheshmanseta.mvvm.utils.AppConstants
import kotlinx.coroutines.Dispatchers

class DataModelMaheshDashboard : ViewModel() {

    fun getData(token: String?, isConnected: Boolean) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = RepositoryMaheshDashboard.getDataFromServer(token, isConnected)))
        } catch (exception: Exception) {
            if (AppConstants.IS_LOG_ENABLED) {
                exception.printStackTrace()
            }
            if(isConnected) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }else{
                emit(Resource.error(data = null, message = exception.message ?: "Check your connection!"))
            }
        }
    }
}