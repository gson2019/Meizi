package com.example.bubble.meizi.model

data class NetResponse<out T>(val status: Status, val data: T?, val message: String?)  {
    companion object {
        fun <T> success(data: T)  = NetResponse(Status.SUCCESS, data, null)
        fun <T> error(data: T?, message: String) = NetResponse(Status.ERROR, data, message)
        fun <T> loading(data: T?) = NetResponse(Status.LOADING, data, null)
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}