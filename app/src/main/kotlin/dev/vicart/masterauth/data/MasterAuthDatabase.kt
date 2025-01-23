package dev.vicart.masterauth.data

import android.content.Context
import androidx.core.content.edit
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import dev.vicart.masterauth.data.dao.OTPKeyDao
import dev.vicart.masterauth.data.entity.OTPKey
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import java.util.UUID

@Database(version = 1, entities = [OTPKey::class])
abstract class MasterAuthDatabase : RoomDatabase() {

    abstract fun otpKeyDao() : OTPKeyDao

    companion object {

        private const val DB_PASSWORD_KEY = "dbpassword"
        lateinit var instance: MasterAuthDatabase

        init {
            System.loadLibrary("sqlcipher")
        }

        fun init(context: Context) {
            val dbPassword = getOrCreatePassword(context)
            val openHelperFactory = SupportOpenHelperFactory(dbPassword.toByteArray())
            instance = Room.databaseBuilder(context, MasterAuthDatabase::class.java, "masterauth.db")
                .openHelperFactory(openHelperFactory)
                .build()
        }

        private fun getOrCreatePassword(context: Context) : String {
            val prefs = EncryptedSharedPreferences.create("MasterAuthEncryptedPref",
                "MasterAuthKey", context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
            if(!prefs.contains(DB_PASSWORD_KEY)) {
                prefs.edit {
                    putString(DB_PASSWORD_KEY, UUID.randomUUID().toString())
                }
            }
            return prefs.getString(DB_PASSWORD_KEY, null)!!
        }
    }
}