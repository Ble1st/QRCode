package com.ble1st.qrcode.core.model

/**
 * Data class representing QR code information
 */
data class QRCodeData(
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
