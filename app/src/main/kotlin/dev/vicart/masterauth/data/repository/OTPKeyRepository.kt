package dev.vicart.masterauth.data.repository

import dev.vicart.masterauth.data.MasterAuthDatabase
import dev.vicart.masterauth.data.entity.OTPKey
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

object OTPKeyRepository {

    private val otpKeyDao = MasterAuthDatabase.instance.otpKeyDao()

    //val otpKeys = otpKeyDao.getAllOTPKeys()
    val otpKeys = flowOf(listOf(OTPKey(uid = 0, label = "testkey", key = "testkeysecret".toByteArray(), period = 30)))
}