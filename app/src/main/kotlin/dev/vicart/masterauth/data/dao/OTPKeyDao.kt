package dev.vicart.masterauth.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.vicart.masterauth.data.entity.OTPKey
import kotlinx.coroutines.flow.Flow

@Dao
interface OTPKeyDao {

    @Query("SELECT * FROM otpkey")
    fun getAllOTPKeys() : Flow<List<OTPKey>>

    @Insert
    fun saveKey(key: OTPKey)

    @Query("DELETE FROM otpkey WHERE uid = :uid")
    fun deleteKey(uid: Long)
}