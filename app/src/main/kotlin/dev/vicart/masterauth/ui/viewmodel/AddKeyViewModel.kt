package dev.vicart.masterauth.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vicart.kotp.common.HashAlgorithm
import dev.vicart.masterauth.data.entity.OTPKey
import dev.vicart.masterauth.data.repository.OTPKeyRepository
import dev.vicart.masterauth.model.AddKeyRequest
import kotlinx.coroutines.launch

class AddKeyViewModel : ViewModel() {

    val issuer = MutableLiveData("")
    val accountName = MutableLiveData("")
    val key = MutableLiveData("")
    val period = MutableLiveData(30L)
    val codeLength = MutableLiveData(6)
    val algorithm = MutableLiveData(HashAlgorithm.SHA1)

    fun addRawKey(onComplete: () -> Unit) {
        val otpKey = AddKeyRequest(
            issuer = issuer.value!!,
            accountName = accountName.value!!,
            key = key.value!!,
            period = period.value!!,
            codeLength = codeLength.value!!,
            algorithm = algorithm.value!!
        )
        viewModelScope.launch {
            OTPKeyRepository.saveKey(otpKey)
            onComplete()
        }
    }
}