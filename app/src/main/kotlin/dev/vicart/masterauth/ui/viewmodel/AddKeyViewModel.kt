package dev.vicart.masterauth.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddKeyViewModel : ViewModel() {

    val label = MutableLiveData("")
    val key = MutableLiveData("")
    val period = MutableLiveData(30L)
    val codeLength = MutableLiveData(6)

    fun addRawKey() {

    }
}