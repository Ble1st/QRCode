package com.ble1st.qrcode.core.common.util

/**
 * Result type for operations that can succeed or fail
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}
