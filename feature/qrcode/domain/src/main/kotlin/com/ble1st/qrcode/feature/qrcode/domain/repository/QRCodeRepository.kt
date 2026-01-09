package com.ble1st.qrcode.feature.qrcode.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import com.ble1st.qrcode.core.model.StorageResult

/**
 * Repository interface for QR code operations
 */
interface QRCodeRepository {
    /**
     * Generate QR code bitmap from text
     */
    suspend fun generateQRCode(text: String, size: Int = 512): Bitmap?
    
    /**
     * Save QR code bitmap to file using Storage Access Framework
     */
    suspend fun saveQRCodeViaSAF(bitmap: Bitmap, uri: Uri): StorageResult
    
    /**
     * Save QR code bitmap to default location
     */
    suspend fun saveQRCodeToFile(bitmap: Bitmap, filename: String? = null): StorageResult
}
