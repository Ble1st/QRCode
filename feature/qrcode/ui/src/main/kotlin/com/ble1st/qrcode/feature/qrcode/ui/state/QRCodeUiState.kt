package com.ble1st.qrcode.feature.qrcode.ui.state

import android.graphics.Bitmap

/**
 * UI state for QR code screen
 */
data class QRCodeUiState(
    val qrCodeBitmap: Bitmap? = null,
    val isGenerating: Boolean = false,
    val errorMessage: String? = null
)
