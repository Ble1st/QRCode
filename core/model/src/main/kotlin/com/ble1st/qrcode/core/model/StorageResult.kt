package com.ble1st.qrcode.core.model

/**
 * Result of storage operations
 */
sealed class StorageResult {
    data class Success(val filePath: String) : StorageResult()
    data class Error(val message: String) : StorageResult()
}
