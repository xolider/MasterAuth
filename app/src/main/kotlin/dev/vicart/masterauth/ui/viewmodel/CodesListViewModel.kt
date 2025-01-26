package dev.vicart.masterauth.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.vicart.masterauth.data.repository.OTPCodesRepository
import dev.vicart.masterauth.data.repository.OTPKeyRepository
import dev.vicart.masterauth.model.AddKeyRequest
import kotlinx.coroutines.launch

class CodesListViewModel : ViewModel() {

    val otpCodes = OTPCodesRepository.otpCodes.asLiveData()

    fun addRawKey(request: AddKeyRequest) {
        viewModelScope.launch {
            OTPKeyRepository.saveKey(request)
        }
    }

    fun addKeyFromQRCode(key: String) {
        val keyRequest = AddKeyRequest.fromUri(Uri.parse(key))
        if(keyRequest != null) {
            addRawKey(keyRequest)
        }
    }

    fun removeKey(uid: Long) {
        viewModelScope.launch {
            OTPKeyRepository.removeKey(uid)
        }
    }
}