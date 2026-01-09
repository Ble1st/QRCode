package com.ble1st.qrcode.feature.qrcode.data.datasource

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.ble1st.qrcode.core.common.util.getTimestamp
import com.ble1st.qrcode.core.model.StorageResult
import timber.log.Timber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

/**
 * Data source for file storage operations
 */
class FileStorageManager @Inject constructor(
    private val context: Context
) {
    
    suspend fun saveQRCodeViaSAF(bitmap: Bitmap, uri: Uri): StorageResult = withContext(Dispatchers.IO) {
        try {
            Timber.d("Saving QR code via SAF to URI: $uri")
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            }
            Timber.d("QR code saved successfully via SAF")
            StorageResult.Success(uri.toString())
        } catch (e: Exception) {
            Timber.e(e, "Failed to save QR code via SAF")
            StorageResult.Error(e.message ?: "Unknown error")
        }
    }
    
    suspend fun saveQRCodeToFile(bitmap: Bitmap, filename: String? = null): StorageResult = withContext(Dispatchers.IO) {
        try {
            val fileName = filename ?: "QRCode_${getTimestamp()}.png"
            Timber.d("Saving QR code to file: $fileName")
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Use MediaStore API for Android 10+
                val contentValues = android.content.ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/QRCode")
                }
                
                val uri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                
                uri?.let {
                    context.contentResolver.openOutputStream(it)?.use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.flush()
                    }
                    Timber.d("QR code saved successfully to MediaStore")
                    StorageResult.Success(uri.toString())
                } ?: run {
                    Timber.e("Failed to create MediaStore entry")
                    StorageResult.Error("Failed to create file")
                }
            } else {
                // Use File API for older Android versions
                val picturesDir = File(context.getExternalFilesDir(null), "Pictures/QRCode")
                if (!picturesDir.exists()) {
                    picturesDir.mkdirs()
                }
                
                val file = File(picturesDir, fileName)
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                }
                Timber.d("QR code saved successfully to file: ${file.absolutePath}")
                StorageResult.Success(file.absolutePath)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to save QR code to file")
            StorageResult.Error(e.message ?: "Unknown error")
        }
    }
}
