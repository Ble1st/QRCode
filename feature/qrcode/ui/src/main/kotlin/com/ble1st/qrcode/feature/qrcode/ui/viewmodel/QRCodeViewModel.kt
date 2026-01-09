package com.ble1st.qrcode.feature.qrcode.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ble1st.qrcode.core.model.StorageResult
import com.ble1st.qrcode.feature.qrcode.domain.usecase.GenerateQRCodeUseCase
import com.ble1st.qrcode.feature.qrcode.domain.usecase.SaveQRCodeUseCase
import com.ble1st.qrcode.feature.qrcode.ui.state.QRCodeUiState
import timber.log.Timber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QRCodeViewModel @Inject constructor(
    private val generateQRCodeUseCase: GenerateQRCodeUseCase,
    private val saveQRCodeUseCase: SaveQRCodeUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(QRCodeUiState())
    val uiState: StateFlow<QRCodeUiState> = _uiState.asStateFlow()
    
    fun generateQRCode(text: String) {
        if (text.isBlank()) {
            Timber.w("Attempted to generate QR code with empty text")
            _uiState.update { it.copy(qrCodeBitmap = null, errorMessage = "Text darf nicht leer sein") }
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isGenerating = true, errorMessage = null) }
            try {
                val bitmap = generateQRCodeUseCase(text)
                _uiState.update { 
                    it.copy(
                        qrCodeBitmap = bitmap,
                        isGenerating = false,
                        errorMessage = if (bitmap == null) "QR-Code konnte nicht generiert werden" else null
                    )
                }
                Timber.d("QR code generated successfully")
            } catch (e: Exception) {
                Timber.e(e, "Failed to generate QR code")
                _uiState.update { 
                    it.copy(
                        isGenerating = false,
                        errorMessage = "Fehler: ${e.message}"
                    )
                }
            }
        }
    }
    
    fun clearQRCode() {
        _uiState.update { it.copy(qrCodeBitmap = null, errorMessage = null) }
        Timber.d("QR code cleared")
    }
    
    
    fun saveQRCode(uri: Uri?) {
        val bitmap = _uiState.value.qrCodeBitmap
        if (bitmap == null) {
            Timber.w("No QR code bitmap available to save")
            _uiState.update { it.copy(errorMessage = "Kein QR-Code zum Speichern vorhanden") }
            return
        }
        
        viewModelScope.launch {
            try {
                val result = saveQRCodeUseCase(bitmap, uri)
                when (result) {
                    is StorageResult.Success -> {
                        Timber.d("QR code saved successfully: ${result.filePath}")
                        _uiState.update { it.copy(errorMessage = null) }
                    }
                    is StorageResult.Error -> {
                        Timber.e("Failed to save QR code: ${result.message}")
                        _uiState.update { it.copy(errorMessage = "Speichern fehlgeschlagen: ${result.message}") }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Exception while saving QR code")
                _uiState.update { it.copy(errorMessage = "Fehler beim Speichern: ${e.message}") }
            }
        }
    }
    
    fun saveQRCodeToGallery() {
        val bitmap = _uiState.value.qrCodeBitmap
        if (bitmap == null) {
            Timber.w("No QR code bitmap available to save")
            _uiState.update { it.copy(errorMessage = "Kein QR-Code zum Speichern vorhanden") }
            return
        }
        
        viewModelScope.launch {
            try {
                val result = saveQRCodeUseCase(bitmap, null, null)
                when (result) {
                    is StorageResult.Success -> {
                        Timber.d("QR code saved to gallery successfully: ${result.filePath}")
                        _uiState.update { it.copy(errorMessage = null) }
                    }
                    is StorageResult.Error -> {
                        Timber.e("Failed to save QR code to gallery: ${result.message}")
                        _uiState.update { it.copy(errorMessage = "Speichern in Galerie fehlgeschlagen: ${result.message}") }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Exception while saving QR code to gallery")
                _uiState.update { it.copy(errorMessage = "Fehler beim Speichern in Galerie: ${e.message}") }
            }
        }
    }
    
    fun getQRCodeBitmap(): android.graphics.Bitmap? {
        return _uiState.value.qrCodeBitmap
    }
}
