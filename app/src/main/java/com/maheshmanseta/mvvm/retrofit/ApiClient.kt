package com.maheshmanseta.mvvm.retrofit

import com.maheshmanseta.mvvm.utils.AppConstants
import com.maheshmanseta.mvvm.utils.AppLog
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var retrofit: Retrofit? = null
    private lateinit var okHttpClient: OkHttpClient

    fun getClient(map: Map<String, String>?): Retrofit? {

//        if (okHttpClient == null)
        initOkHttp(headerData = map as Map<String?, String>)

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun initOkHttp(headerData: Map<String?, String>) {
        val REQUEST_TIMEOUT = 60
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        httpClient.interceptors().add(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Accept", "application/json")
            val keys: Set<*> = headerData.keys
            for (key1 in keys) {
                val key = key1 as String?
                val value = headerData[key]
                if (key != null && value != null) {
                    requestBuilder.addHeader(key, value)
                    AppLog.e("Data", "$key = $value")
                }
            }
            chain.proceed(requestBuilder.build())
        })
        if (AppConstants.IS_LOG_ENABLED) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        httpClient.interceptors().add(interceptor)
        okHttpClient = httpClient.build()
    }

}