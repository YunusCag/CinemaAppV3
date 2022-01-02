package com.yunuscagliyan.cinemaapp.presentation.state

sealed class NetworkState(val message: String? = null) {
    object Loading : NetworkState()
    object Success : NetworkState()
    class Error(message: String?) : NetworkState(message = message)
}
