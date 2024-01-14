package com.example.signinintroduce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
//    private val TAG = "mine"

    //    val pwInputRegex = """^[0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]*$"""
    val pwFinalRegex =
        """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~])[0-9a-zA-Z0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]{8,16}$"""

    //    val pwInputPattern: Pattern = Pattern.compile(pwInputRegex)
    val pwFinalPattern: Pattern = Pattern.compile(pwFinalRegex)

    private val _btnChallSignupIsEnabled = MutableLiveData<Boolean>(false)
    val btnChallSignupIsEnabled: LiveData<Boolean> get() = _btnChallSignupIsEnabled

    private val _spnrChallMailIsVisible = MutableLiveData<Boolean>(true)
    val spnrChallMailIsVisible: LiveData<Boolean> get() = _spnrChallMailIsVisible

    private val _etChallPasswordText = MutableLiveData<String>("")
//    val etChallPasswordText: LiveData<String> get() = _etChallPasswordText

    private val _tvChallPasswordWarnText = MutableLiveData<String>("")
    val tvChallPasswordWarnText: LiveData<String> get() = _tvChallPasswordWarnText

    private val _tvChallPasswordWarnStartAnimation = MutableLiveData<Boolean>(false)
    val tvChallPasswordWarnStartAnimation: LiveData<Boolean> get() = _tvChallPasswordWarnStartAnimation
    fun setTvChallPasswordWarnStartAnimation(boolean: Boolean) {
        _tvChallPasswordWarnStartAnimation.value = boolean
    }

    private val _etChallPasswordSetSelection = MutableLiveData<Int>(0)
    val etChallPasswordSetSelection: LiveData<Int> get() = _etChallPasswordSetSelection

    private val _tvChallPasswordLengthText = MutableLiveData<String>("")
    val tvChallPasswordLengthText: LiveData<String> get() = _tvChallPasswordLengthText

    private val _etChallNameText = MutableLiveData<String>("")
//    val etChallNameText: LiveData<String> get() = _etChallNameText

    private val _tvChallNameWarnText = MutableLiveData<String>("")
    val tvChallNameWarnText: LiveData<String> get() = _tvChallNameWarnText

    private val _etChallMailText = MutableLiveData<String>("")
//    val etChallMailText: LiveData<String> get() = _etChallMailText

    private val _etChallDomainText = MutableLiveData<String>("")
//    val etChallDomainText: LiveData<String> get() = _etChallDomainText

    private val _tvChallMailWarnText = MutableLiveData<String>("")
    val tvChallMailWarnText: LiveData<String> get() = _tvChallMailWarnText

    private val _tvMailWarnText = MutableLiveData("")
    val tvMailWarnText: LiveData<String> get() = _tvMailWarnText

    private val _etChallVerifyText = MutableLiveData("")
//    val etChallVerifyText: LiveData<String> get() = _etChallVerifyText

    private val _tvChallVerifyWarnText = MutableLiveData("")
    val tvChallVerifyWarnText: LiveData<String> get() = _tvChallVerifyWarnText

    private var okName = false
    private var okMail = false

    private var okDomain = false
    private var okPw = false
    private var okVerify = false

//    val pwWatcher by lazy {
//        object : TextWatcher {
//            private var beforePw = ""
//            private var beforeCursor = 0
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                beforePw = _etChallPasswordText.value ?: ""
//                beforeCursor = start
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            // 사용자의 입력을 막아버리는 것은 UX상 좋지 않다. 안내를 해주는 편이 좋다.
//            // 한글은 조합때문에 입력받고 무시하기 어려울텐데, inputType textPassword 가 알아서 막아주네.
//            // 원표시₩는 그냥 입력이 안됨.
//            override fun afterTextChanged(s: Editable?) {
//                if (!pwInputPattern.matcher(_etChallPasswordText.value ?: "").matches()) {
//                    _tvChallPasswordWarnText.value = ResourcesProvider.getString(R.string.chall_warn_forbidden_input)
//                    _tvChallPasswordWarnStartAnimation.value = true
////                    _etChallPassword.removeTextChangedListener(this)
//                    _etChallPasswordText.value = beforePw
//                    _etChallPasswordSetSelection.value = beforeCursor
////                    binding.etChallPassword.addTextChangedListener(this)
//                    return
//                }
//                "${_etChallPasswordText.value?.length}/16".also { _tvChallPasswordLengthText.value = it }
//                check(_etChallPasswordText.value)
//                Log.d(TAG, "== back from check ==")
//            }
//        }
//    }

//    private var checkCount = 0  // for debug
    fun check(v: EnumView, text: String = "") {
//        Log.d(
//            TAG, "v: ${
//                v.toString().run { substring(length - 36, length - 15) }
//            }, check count: ${++checkCount}"
//        )
        when (v) {
            EnumView.ET_CHALL_NAME -> {
                _etChallNameText.value = text
                if (text.isEmpty()) {
                    _tvChallNameWarnText.value =
                        ResourcesProvider.getString(R.string.chall_warn_enter_name)
                    okName = false
                } else {
                    _tvChallNameWarnText.value = ""
                    okName = true
                }
            }

            EnumView.ET_CHALL_MAIL, EnumView.ET_CHALL_DOMAIN -> {
                if (v == EnumView.ET_CHALL_MAIL) _etChallMailText.value = text
                else _etChallDomainText.value = text

                okMail = _etChallMailText.value?.isNotEmpty() ?: false
                okDomain = _etChallDomainText.value?.isNotEmpty() ?: false

                if (_spnrChallMailIsVisible.value == true) {
                    if (!okMail) _tvChallMailWarnText.value =
                        ResourcesProvider.getString(R.string.signup_warn_enter_email)
                    else _tvChallMailWarnText.value = ""
                } else {
                    if (!okMail) _tvChallMailWarnText.value =
                        "이메일${if (!okDomain) ResourcesProvider.getString(R.string.signup_warn_and_domain) else ""}을 입력해주세요."
                    else if (!okDomain) _tvChallMailWarnText.value =
                        ResourcesProvider.getString(R.string.signup_warn_enter_domain)
                    else _tvChallMailWarnText.value = ""
                }
            }

            EnumView.ET_CHALL_PASSWORD -> {
                _etChallPasswordText.value = text
                val pw = _etChallPasswordText.value.toString()
                if (_etChallPasswordText.value!!.isEmpty()) {
                    _tvChallPasswordWarnText.value =
                        ResourcesProvider.getString(R.string.chall_warn_enter_password)
                    okPw = false
                } else if (pw.length < 8) {
                    _tvChallPasswordWarnText.value =
                        ResourcesProvider.getString(R.string.chall_warn_pw_under_8)
                    okPw = false
                } else if (pw.length > 16) {
                    _tvChallPasswordWarnText.value =
                        ResourcesProvider.getString(R.string.chall_warn_pw_over_16)
                    okPw = false
                } else if (!pwFinalPattern.matcher(pw).matches()) {
                    _tvChallPasswordWarnText.value =
                        ResourcesProvider.getString(R.string.chall_warn_pw_regex)
                    okPw = false
                    // TODO: 동일한 문자를 많이 포함한 경우, 이름이나 메일 등 개인정보.
                } else {
                    _tvChallPasswordWarnText.value = ""
                    okPw = true
                }
            }

            EnumView.ET_CHALL_VERIFY -> {
                _etChallVerifyText.value = text
                if (_etChallVerifyText.value == _etChallPasswordText.value) {
                    _tvChallVerifyWarnText.value = ""
                    okVerify = true
                } else {
                    _tvChallVerifyWarnText.value =
                        ResourcesProvider.getString(R.string.chall_warn_verify_not_same)
                    okVerify = false
                }
            }

            else -> {}
        }

        _btnChallSignupIsEnabled.value =
            okName && okMail && (_spnrChallMailIsVisible.value!! || okDomain) && okPw && okVerify
    }
}