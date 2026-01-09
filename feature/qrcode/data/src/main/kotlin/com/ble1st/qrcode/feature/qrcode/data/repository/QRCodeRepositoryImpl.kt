package com.ble1st.qrcode.feature.qrcode.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.ble1st.qrcode.core.model.StorageResult
import com.ble1st.qrcode.feature.qrcode.data.datasource.FileStorageManager
import com.ble1st.qrcode.feature.qrcode.data.datasource.QRCodeGenerator
import com.ble1st.qrcode.feature.qrcode.domain.repository.QRCodeRepository
import javax.inject.Inject

/**
 * Repository implementation for QR code operations
 */
class QRCodeRepositoryImpl @Inject constructor(
    private val qrCodeGenerator: QRCodeGenerator,
    private val fileStorageManager: FileStorageManager
) : QRCodeRepository {
    
    override suspend fun generateQRCode(text: String, size: Int): Bitmap? {
        return qrCodeGenerator.generateQRCode(text, size)
    }
    
    override suspend fun saveQRCodeViaSAF(bitmap: Bitmap, uri: Uri): StorageResult {
        return fileStorageManager.saveQRCodeViaSAF(bitmap, uri)
    }
    
    override suspend fun saveQRCodeToFile(bitmap: Bitmap, filename: String?): StorageResult {
        return fileStorageManager.saveQRCodeToFile(bitmap, filename)
    }
}
