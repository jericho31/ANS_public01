package com.example.signinintroduce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    private val _text1 = MutableLiveData<String>()
    val text1: LiveData<String> get() = _text1
    fun setText1(text: String) {
        _text1.value = text
    }
}