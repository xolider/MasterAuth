package dev.vicart.masterauth.model

data class OTPCode(
    val label: String,
    val code: String,
    val nextEmit: Long,
    val period: Long
)
