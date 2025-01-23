package dev.vicart.masterauth

import android.app.Application
import dev.vicart.masterauth.data.MasterAuthDatabase

class MasterAuthApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MasterAuthDatabase.init(this)
    }
}