package com.ble1st.qrcode

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ble1st.qrcode.core.designsystem.theme.QRCodeTheme
import com.ble1st.qrcode.feature.qrcode.ui.screen.MainScreen
import com.ble1st.qrcode.feature.qrcode.ui.viewmodel.QRCodeViewModel
import timber.log.Timber
import dagger.hilt.android.AndroidEntryPoint

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
        
        // Set callback for URI handling - this will be called when URI is received
        remember {
            saveUriCallback = { uri ->
                viewModel.saveQRCode(uri)
            }
        }
        
        MainScreen(
            viewModel = viewModel,
            onSaveClick = { 
                saveFileLauncher.launch("QRCode_${com.ble1st.qrcode.core.common.util.getTimestamp()}.png")
            }
        )
    }
}
