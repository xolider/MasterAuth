package dev.vicart.masterauth.data.repository

import dev.vicart.masterauth.data.MasterAuthDatabase
import dev.vicart.masterauth.data.entity.OTPKey
import dev.vicart.masterauth.model.AddKeyRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object OTPKeyRepository {

    private val otpKeyDao = MasterAuthDatabase.instance.otpKeyDao()

    val otpKeys = otpKeyDao.getAllOTPKeys()

    suspend fun saveKey(request: AddKeyRequest) = withContext(Dispatchers.IO) {
        val key = OTPKey.fromRequest(request)
        otpKeyDao.saveKey(key)
    }

    suspend fun removeKey(uid: Long) = withContext(Dispatchers.IO) {
        otpKeyDao.deleteKey(uid)
    }
}