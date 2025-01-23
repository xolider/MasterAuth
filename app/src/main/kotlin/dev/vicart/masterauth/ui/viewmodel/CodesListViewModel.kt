package dev.vicart.masterauth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.vicart.masterauth.data.repository.OTPCodesRepository
import dev.vicart.masterauth.model.AddKeyRequest

class CodesListViewModel : ViewModel() {


    val otpCodes = OTPCodesRepository.otpCodes.asLiveData()

    fun addRawKey(request: AddKeyRequest) {

    }

    fun addKeyFromQRCode(key: String) {

    }
}