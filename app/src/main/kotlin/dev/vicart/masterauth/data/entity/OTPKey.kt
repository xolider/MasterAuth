package dev.vicart.masterauth.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "otpkey")
data class OTPKey(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "key") val key: ByteArray,
    @ColumnInfo(name = "period") val period: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OTPKey

        if (uid != other.uid) return false
        if (label != other.label) return false
        if (!key.contentEquals(other.key)) return false
        if(period != other.period) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid?.hashCode() ?: 0
        result = 31 * result + label.hashCode()
        result = 31 * result + key.contentHashCode()
        result = 31 * result + period.hashCode()
        return result
    }
}
