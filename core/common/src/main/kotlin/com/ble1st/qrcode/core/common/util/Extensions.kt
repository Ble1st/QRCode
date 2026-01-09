package com.ble1st.qrcode.core.common.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Generate timestamp string for filenames
 */
fun getTimestamp(): String {
    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    return sdf.format(Date())
}
