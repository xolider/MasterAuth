package dev.vicart.masterauth.ui.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.tasks.await

class BarcodeScanning(context: Context) {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAutoZoom()
        .build()
    private val scanner = GmsBarcodeScanning.getClient(context, options)

    suspend fun scanQrCode() : String? {
        try {
            val result = scanner.startScan().await()
            return result.rawValue
        } catch (e: Exception) {

        }
        return null
    }
}

@Composable
fun rememberBarcodeScanning() : BarcodeScanning {
    val context = LocalContext.current
    return remember(context) {
        BarcodeScanning(context)
    }
}