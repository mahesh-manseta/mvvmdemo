package com.maheshmanseta.mvvm.utils

import android.util.Log

object AppLog {
    fun e(tag: String, message: String) {
        if (AppConstants.IS_LOG_ENABLED)
            Log.e("MAHESH_$tag", message)
    }

    fun e(tag: String, message: Int) {
        if (AppConstants.IS_LOG_ENABLED)
            Log.e("MAHESH_$tag", message.toString())
    }

    fun e(tag: String, message: Boolean) {
        if (AppConstants.IS_LOG_ENABLED)
            Log.e("MAHESH_$tag", message.toString())
    }

    fun e(tag: String, message: Double) {
        if (AppConstants.IS_LOG_ENABLED)
            Log.e("MAHESH_$tag", message.toString())
    }
}