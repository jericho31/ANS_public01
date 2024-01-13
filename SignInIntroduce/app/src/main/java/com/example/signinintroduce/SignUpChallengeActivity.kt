package com.example.signinintroduce

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.signinintroduce.databinding.ActivitySignUpChallengeBinding
import java.util.regex.Pattern

class SignUpChallengeActivity : AppCompatActivity() {
    val pwInputRegex = """^[0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]*$"""
    val pwFinalRegex =
        """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~])[0-9a-zA-Z0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]{8,16}$"""
    val pwInputPattern: Pattern = Pattern.compile(pwInputRegex)
    val pwFinalPattern: Pattern = Pattern.compile(pwFinalRegex)
    
    private val TAG = "mine"
    private lateinit var binding: ActivitySignUpChallengeBinding
    private lateinit var viewModel: SignUpViewModel

    companion object {
        // 나중엔 서버에서 받아오는 식.
        val mails = arrayOf("gmail.com", "kakao.com", "naver.com", "직접 입력")
    }

    // EditText 배열
    private val arrET by lazy { arrayOf(binding.etChallName, binding.etChallMail, binding.etChallDomain, binding.etChallPassword, binding.etChallVerify) }

    private var okName = false
    private var okMail = false

    // TODO: okDomain이랑 스피너 visible 두 조건 어떻게 잘 좀 통합
    private var okDomain = false
    private var okPw = false
    private var okVerify = false

    private val anim = AlphaAnimation(0.125f, 0.625f).apply {
        duration = 100L
        repeatCount = 3
    }
    private val pwWatcher by lazy {
        object : TextWatcher {
            private var beforePw = ""
            private var beforeCursor = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "before:: start: $start, count: $count, after: $after")
                Log.d(TAG, "$s/selection: ${binding.etChallPassword.selectionStart}, ${binding.etChallPassword.selectionEnd}")
                beforePw = binding.etChallPassword.text.toString()
                beforeCursor = start
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(
                    TAG,
                    "on:: start: $start, before: $before, count: $count"
                )
                Log.d(
                    TAG,
                    "$s/selection: ${binding.etChallPassword.selectionStart}, ${binding.etChallPassword.selectionEnd}"
                )
            }

            // 사용자의 입력을 막아버리는 것은 UX상 좋지 않다. 안내를 해주는 편이 좋다.
            // 한글은 조합때문에 입력받고 무시하기 어려울텐데, inputType textPassword 가 알아서 막아주네.
            // 원표시₩는 그냥 입력이 안됨.
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "after:: $s/selection: ${binding.etChallPassword.selectionStart}, ${binding.etChallPassword.selectionEnd}")
                if (!pwInputPattern.matcher(binding.etChallPassword.text.toString()).matches()) {
                    // warn 바꾸는 쪽으로. 그냥 바꾸면 잘 안보일듯. 깜빡여야. 그러려면 코루틴 블라킹으로...?
                    // AlphaAnimation 이라는 좋은 기능이 있었다. 역시 구글갓
                    binding.tvChallPasswordWarn.text = getString(R.string.chall_warn_forbidden_input)
                    binding.tvChallPasswordWarn.startAnimation(anim)
                    // TODO: InputFilter - 일단 구현했으니 다음 기회에.
                    binding.etChallPassword.removeTextChangedListener(this)
                    binding.etChallPassword.setText(beforePw)
                    binding.etChallPassword.setSelection(beforeCursor)
                    binding.etChallPassword.addTextChangedListener(this)
                    return
                }
                "${binding.etChallPassword.text.length}/16".also { binding.tvChallPasswordLength.text = it }
                check(binding.etChallPassword)
                Log.d(TAG, "== back from check ==")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        initViewModel(viewModel)

        // 처음에 기본으로 첫번째꺼가 선택되어 있는 이유는 어레이어댑터라서 그런가?
        binding.spnrChallMail.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mails)

        arrET.forEach { et -> if ((et == binding.etChallPassword).not()) et.addTextChangedListener { check(et) } }
        binding.etChallPassword.addTextChangedListener(pwWatcher)
        val onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus -> if (!hasFocus) check(v!!) }
        arrET.forEach { it.onFocusChangeListener = onFocusChangeListener }

        binding.btnChallSignup.setOnClickListener {
            if (binding.spnrChallMail.isVisible) {
                intent.putExtra(Extra.id, "${binding.etChallMail.text}@${binding.spnrChallMail.selectedItem}")
            } else {
                intent.putExtra(Extra.id, "${binding.etChallMail.text}@${binding.etChallDomain.text}")
            }
            intent.putExtra(Extra.password, binding.etChallPassword.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.spnrChallMail.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    if (position == binding.spnrChallMail.adapter.count - 1) {
                        binding.etChallDomain.isVisible = true
                        binding.spnrChallMail.isVisible = false
                    }

                    check(binding.spnrChallMail)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun initViewModel(vm: SignUpViewModel) {}

    private var checkCount = 0  // for debug
    fun check(v: View) {
        Log.d(
            TAG, "v: ${
                v.toString().run { substring(length - 36, length - 15) }
            }, check count: ${++checkCount}"
        )
        when (v) {
            binding.etChallName -> {
                if (binding.etChallName.text.isEmpty()) {
                    binding.tvChallNameWarn.text = getString(R.string.chall_warn_enter_name)
                    okName = false
                } else {
                    binding.tvChallNameWarn.text = ""
                    okName = true
                }
            }

            binding.etChallMail, binding.etChallDomain -> {
                okMail = binding.etChallMail.text.isNotEmpty()
                okDomain = binding.etChallDomain.text.isNotEmpty()

                if (binding.spnrChallMail.isVisible) {
                    if (!okMail) binding.tvChallMailWarn.text = getString(R.string.signup_warn_enter_email)
                    else binding.tvChallMailWarn.text = ""
                } else {
                    if (!okMail) binding.tvChallMailWarn.text = getString(
                        R.string.signup_warn_enter_email_format,
                        if (!okDomain) getString(R.string.signup_warn_and_domain) else ""
                    )
                    else if (!okDomain) binding.tvChallMailWarn.text =
                        getString(R.string.signup_warn_enter_domain)
                    else binding.tvChallMailWarn.text = ""
                }
            }

            binding.etChallPassword -> {
                val pw = binding.etChallPassword.text.toString()
                if (binding.etChallPassword.text.isEmpty()) {
                    binding.tvChallPasswordWarn.text = getString(R.string.chall_warn_enter_password)
                    okPw = false
                } else if (pw.length < 8) {
                    binding.tvChallPasswordWarn.text = getString(R.string.chall_warn_pw_under_8)
                    okPw = false
                } else if (pw.length > 16) {
                    binding.tvChallPasswordWarn.text = getString(R.string.chall_warn_pw_over_16)
                    okPw = false
                } else if (!pwFinalPattern.matcher(pw).matches()) {
                    binding.tvChallPasswordWarn.text = getString(R.string.chall_warn_pw_regex)
                    okPw = false
                    // TODO: 동일한 문자를 많이 포함한 경우, 이름이나 메일 등 개인정보.
                } else {
                    binding.tvChallPasswordWarn.text = ""
                    okPw = true
                }
            }

            binding.etChallVerify -> {
                if (binding.etChallVerify.text.contentEquals(binding.etChallPassword.text)) {
                    binding.tvChallVerifyWarn.text = ""
                    okVerify = true
                } else {
                    binding.tvChallVerifyWarn.text = getString(R.string.chall_warn_verify_not_same)
                    okVerify = false
                }
            }
        }

        binding.btnChallSignup.isEnabled = okName && okMail && (binding.spnrChallMail.isVisible || okDomain) && okPw && okVerify
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}