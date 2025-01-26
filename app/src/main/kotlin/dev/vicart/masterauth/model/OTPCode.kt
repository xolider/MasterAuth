package dev.vicart.masterauth.model

data class OTPCode(
    val uid: Long,
    val issuer: String,
    val accountName: String,
    val code: String,
    val nextEmit: Long,
    val period: Long
)
