package com.ble1st.qrcode.feature.qrcode.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ble1st.qrcode.core.designsystem.components.QRCodeCard
import com.ble1st.qrcode.feature.qrcode.ui.viewmodel.QRCodeViewModel

@Composable
fun MainScreen(
    viewModel: QRCodeViewModel = hiltViewModel(),
    onSaveClick: () -> Unit = {},
    onSaveToGalleryClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var inputText by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Input Field
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Text oder URL eingeben") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 5,
            placeholder = { Text("z.B. https://example.com") }
        )
        
        // Generate Button
        Button(
            onClick = { viewModel.generateQRCode(inputText) },
            enabled = !uiState.isGenerating && inputText.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isGenerating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("QR-Code generieren")
        }
        
        // Error Message
        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        
        // QR Code Display
        uiState.qrCodeBitmap?.let { bitmap ->
            QRCodeCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .size(512.dp)
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit
                )
            }
            
            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Speicherplatz wählen")
                }
                
                Button(
                    onClick = onSaveToGalleryClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("In Galerie speichern")
                }
                
                Button(
                    onClick = onShareClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Teilen")
                }
            }
        }
    }
}
