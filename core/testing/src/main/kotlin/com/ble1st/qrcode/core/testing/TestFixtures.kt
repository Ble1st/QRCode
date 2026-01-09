package com.ble1st.qrcode.core.testing

import com.ble1st.qrcode.core.model.QRCodeData

/**
 * Test fixtures for unit tests
 */
object TestFixtures {
    const val TEST_TEXT = "https://example.com"
    const val TEST_URL = "https://www.google.com"
    const val TEST_LONG_TEXT = "A".repeat(1000)
    const val TEST_EMPTY_TEXT = ""
    
    fun createQRCodeData(text: String = TEST_TEXT): QRCodeData {
        return QRCodeData(text = text)
    }
}
