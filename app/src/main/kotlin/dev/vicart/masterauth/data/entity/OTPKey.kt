package dev.vicart.masterauth.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.vicart.kotp.common.HashAlgorithm
import dev.vicart.masterauth.model.AddKeyRequest
import org.apache.commons.codec.binary.Base32

@Entity(tableName = "otpkey")
data class OTPKey(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo(name = "issuer") val issuer: String,
    @ColumnInfo(name = "account_name") val accountName: String,
    @ColumnInfo(name = "key") val key: ByteArray,
    @ColumnInfo(name = "period") val period: Long,
    @ColumnInfo(name = "code_length") val codeLength: Int,
    @ColumnInfo(name = "hash_alog") val algorithm: HashAlgorithm
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OTPKey

        if (uid != other.uid) return false
        if (issuer != other.issuer) return false
        if(accountName != other.accountName) return false
        if (!key.contentEquals(other.key)) return false
        if(period != other.period) return false
        if(codeLength != other.codeLength) return false
        if(algorithm != other.algorithm) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid?.hashCode() ?: 0
        result = 31 * result + issuer.hashCode()
        result = 31 * result + accountName.hashCode()
        result = 31 * result + key.contentHashCode()
        result = 31 * result + period.hashCode()
        result = 31 * result + codeLength.hashCode()
        result = 31 * result + algorithm.hashCode()
        return result
    }

    companion object {
        fun fromRequest(request: AddKeyRequest) : OTPKey = OTPKey(
            issuer = request.issuer,
            accountName = request.accountName,
            codeLength = request.codeLength,
            algorithm = request.algorithm,
            period = request.period,
            key = Base32().decode(request.key)
        )
    }
}
