package com.ble1st.qrcode.feature.qrcode.domain.usecase

import android.graphics.Bitmap
import com.ble1st.qrcode.core.common.util.Constants
import com.ble1st.qrcode.feature.qrcode.domain.repository.QRCodeRepository
import javax.inject.Inject

/**
 * Use case for generating QR codes
 */
class GenerateQRCodeUseCase @Inject constructor(
    private val repository: QRCodeRepository
) {
    suspend operator fun invoke(
        text: String,
        size: Int = Constants.DEFAULT_QR_CODE_SIZE
    ): Bitmap? {
        if (text.isBlank()) {
            return null
        }
        return repository.generateQRCode(text, size)
    }
}
