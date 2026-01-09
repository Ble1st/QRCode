package com.ble1st.qrcode

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.ble1st.qrcode.core.common.util.getTimestamp
import com.ble1st.qrcode.core.designsystem.theme.QRCodeTheme
import com.ble1st.qrcode.feature.qrcode.ui.screen.MainScreen
import com.ble1st.qrcode.feature.qrcode.ui.viewmodel.QRCodeViewModel
import timber.log.Timber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private var saveUriCallback: ((Uri) -> Unit)? = null
    
    private val saveFileLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("image/png")
    ) { uri: Uri? ->
        uri?.let { 
            Timber.d("File save URI received: $uri")
            saveUriCallback?.invoke(it)
            saveUriCallback = null
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            QRCodeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreenContent()
                }
            }
        }
    }
    
    @Composable
    private fun MainScreenContent() {
        val viewModel: QRCodeViewModel = hiltViewModel()
        
        MainScreen(
            viewModel = viewModel,
            onSaveClick = {
                // Set callback for URI handling before launching file picker
                saveUriCallback = { uri ->
                    viewModel.saveQRCode(uri)
                }
                saveFileLauncher.launch("QRCode_${getTimestamp()}.png")
            },
            onSaveToGalleryClick = {
                viewModel.saveQRCodeToGallery()
            },
            onShareClick = {
                shareQRCode(viewModel)
            }
        )
    }
    
    private fun shareQRCode(viewModel: QRCodeViewModel) {
        val bitmap = viewModel.getQRCodeBitmap()
        if (bitmap == null) {
            Timber.w("No QR code bitmap available to share")
            return
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Create temporary file in cache directory
                val cacheDir = File(cacheDir, "qrcode")
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs()
                }
                
                val fileName = "QRCode_${getTimestamp()}.png"
                val file = File(cacheDir, fileName)
                
                // Save bitmap to file
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                }
                
                // Get URI using FileProvider
                val uri = FileProvider.getUriForFile(
                    this@MainActivity,
                    "${packageName}.fileprovider",
                    file
                )
                
                // Create share intent
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = "image/png"
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                
                // Launch share dialog
                val chooserIntent = Intent.createChooser(shareIntent, "QR-Code teilen")
                chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(chooserIntent)
                
                Timber.d("QR code shared successfully")
            } catch (e: Exception) {
                Timber.e(e, "Failed to share QR code")
            }
        }
    }
}
