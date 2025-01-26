package dev.vicart.masterauth.data.repository

import dev.vicart.kotp.totp.TOTPGenerator
import dev.vicart.masterauth.data.entity.OTPKey
import dev.vicart.masterauth.model.OTPCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import java.time.Duration
import java.time.Instant

object OTPCodesRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    val otpCodes = OTPKeyRepository.otpKeys.mapLatest {
        it.map { generateFlowForKey(it) }
    }

    private fun generateCodeForKey(key: OTPKey, nextEmit: Long) : OTPCode {
        val generator = TOTPGenerator(periodSecond = key.period, codeLength = key.codeLength, algorithm = key.algorithm)
        val code = generator.generateCode(key.key)

        return OTPCode(
            issuer = key.issuer,
            code = code,
            nextEmit = nextEmit,
            period = key.period,
            accountName = key.accountName,
            uid = key.uid!!
        )
    }

    private fun generateFlowForKey(key: OTPKey) = flow {
        while (true) {
            val nextEmit = key.period - (Instant.now().epochSecond % key.period)
            emit(generateCodeForKey(key, nextEmit))
            delay(Duration.ofSeconds(nextEmit).toMillis())
        }
    }
}