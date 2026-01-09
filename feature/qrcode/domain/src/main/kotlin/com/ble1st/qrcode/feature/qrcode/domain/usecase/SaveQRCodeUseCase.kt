package com.ble1st.qrcode.feature.qrcode.domain.usecase

import android.graphics.Bitmap
import android.net.Uri
import com.ble1st.qrcode.core.model.StorageResult
import com.ble1st.qrcode.feature.qrcode.domain.repository.QRCodeRepository
import javax.inject.Inject

/**
 * Use case for saving QR codes
 */
class SaveQRCodeUseCase @Inject constructor(
    private val repository: QRCodeRepository
) {
    suspend operator fun invoke(
        bitmap: Bitmap,
        uri: Uri? = null,
        filename: String? = null
    ): StorageResult {
        return if (uri != null) {
            repository.saveQRCodeViaSAF(bitmap, uri)
        } else {
            repository.saveQRCodeToFile(bitmap, filename)
        }
    }
}
