package dev.vicart.masterauth.model

import android.net.Uri
import dev.vicart.kotp.common.HashAlgorithm

data class AddKeyRequest(
    val issuer: String,
    val accountName: String,
    val key: String,
    val algorithm: HashAlgorithm = HashAlgorithm.SHA1,
    val period: Long = 30,
    val codeLength: Int = 6
) {
    companion object {
        fun fromUri(uri: Uri) : AddKeyRequest? {
            if(uri.scheme != "otpauth") return null
            if(uri.host != "totp") return null
            val label = uri.lastPathSegment ?: return null
            val labelSplit = label.split(':')
            val issuer = if(labelSplit.size > 1) labelSplit[0] else uri.getQueryParameter("issuer")
            val accountName = labelSplit.last()
            val base32Secret = uri.getQueryParameter("secret") ?: return null
            val algorithm = uri.getQueryParameter("algorithm")?.let(HashAlgorithm::valueOf) ?: HashAlgorithm.SHA1
            val codeLength = uri.getQueryParameter("digits")?.let(String::toInt) ?: 6
            val period = uri.getQueryParameter("period")?.let(String::toLong) ?: 30
            return AddKeyRequest(
                issuer = issuer ?: "",
                accountName = accountName,
                key = base32Secret,
                algorithm = algorithm,
                codeLength = codeLength,
                period = period
            )
        }
    }
}
