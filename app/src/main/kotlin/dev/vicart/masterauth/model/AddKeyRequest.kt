package dev.vicart.masterauth.model

import dev.vicart.kotp.common.HashAlgorithm

data class AddKeyRequest(
    val label: String,
    val key: String,
    val algorithm: HashAlgorithm = HashAlgorithm.SHA1,
    val period: Long = 30,
    val codeLength: Int = 6
)
