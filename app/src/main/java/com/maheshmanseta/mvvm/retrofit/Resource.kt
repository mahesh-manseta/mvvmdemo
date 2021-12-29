package com.maheshmanseta.mvvm.retrofit

data class Resource<out T>(val status: RetroEnum, val data: T?, val message: String?, val errors: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = RetroEnum.SUCCESS, data = data, message = null, errors = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = RetroEnum.ERROR, data = data, message = message, errors = null)

        fun <T> errorNew(data: T?, message: String, errors: String?): Resource<T> =
            Resource(status = RetroEnum.ERROR, data = data, message = message, errors)

        fun <T> loading(data: T?): Resource<T> = Resource(status = RetroEnum.LOADING, data = data, message = null, errors = null)
    }
}