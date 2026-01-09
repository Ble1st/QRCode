package com.ble1st.qrcode.feature.qrcode.data.datasource

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source for QR code generation
 */
class QRCodeGenerator @Inject constructor() {
    
    fun generateQRCode(text: String, size: Int = 512): Bitmap? {
        return try {
            if (text.isBlank()) {
                Timber.w("Empty text provided for QR code generation")
                return null
            }
            
            Timber.d("Generating QR code for text: ${text.take(50)}...")
            
            val hints = hashMapOf<EncodeHintType, Any>().apply {
                put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M)
                put(EncodeHintType.CHARACTER_SET, "UTF-8")
            }
            
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size, hints)
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
            
            // Convert bitMatrix to Bitmap
            for (x in 0 until size) {
                for (y in 0 until size) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            
            Timber.d("QR code generated successfully")
            bitmap
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate QR code")
            null
        }
    }
}
