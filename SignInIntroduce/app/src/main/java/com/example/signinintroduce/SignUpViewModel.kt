package com.example.signinintroduce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    val pwInputRegex = """^[0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]*$"""
    val pwFinalRegex =
        """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~])[0-9a-zA-Z0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]{8,16}$"""
    val pwInputPattern: Pattern = Pattern.compile(pwInputRegex)
    val pwFinalPattern: Pattern = Pattern.compile(pwFinalRegex)

    private val _text1 = MutableLiveData<String>()
    val text1: LiveData<String> get() = _text1
    fun setText1(text: String) {
        _text1.value = text
    }
}