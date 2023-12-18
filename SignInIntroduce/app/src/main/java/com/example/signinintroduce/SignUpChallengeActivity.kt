package com.example.signinintroduce

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

// TODO: to xml
// 입력 길이도 정규식에서 체크할 수 있지만 메세지를 달리 하려면 체크하는 쪽에서도 해야 함.
//val sample = """^[0-9a-zA-Z!@#$%^+\-=]*$"""  // 참고용
//val sample = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""  // 참고용
//val sample = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"  // 참고용
//val sample = "^(?=.*[A-Za-z])(?=.*[$@$!%*#?&.])[A-Za-z$@$!%*#?&.]{8,20}$"  // 참고용
//val sample = "^(?=.*[0-9])(?=.*[$@$!%*#?&.])[[0-9]$@$!%*#?&.]{8,20}$"  // 참고용
val pwInputRegex = """^[0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]*$"""
val pwFinalRegex =
    """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~])[0-9a-zA-Z0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[\]^_`{|}~]{8,16}$"""
val pwInputPattern = Pattern.compile(pwInputRegex)
val pwFinalPattern = Pattern.compile(pwFinalRegex)

class SignUpChallengeActivity : AppCompatActivity() {
    // 클래스 변수명 정규식인듯: """_?[a-z][A-Za-z\d]*"""
    private val TAG = "mine"

    companion object {
        // TODO: 나중에 xml로.
        val mails = arrayOf("gmail.com", "kakao.com", "naver.com", "직접 입력")
    }

    // TODO: 리스트로 모아넣고, 리스너 세팅 확장함수로 순회.
    private val etName: EditText by lazy { findViewById(R.id.et_chall_name) }
    private val tvNameWarn: TextView by lazy { findViewById(R.id.tv_chall_name_warn) }
    private val etMail: EditText by lazy { findViewById(R.id.et_chall_mail) }
    private val etDomain: EditText by lazy { findViewById(R.id.et_chall_domain) }
    private val tvMailWarn: TextView by lazy { findViewById(R.id.tv_chall_mail_warn) }
    private val etPw: EditText by lazy { findViewById(R.id.et_chall_password) }
    private val tvPwWarn: TextView by lazy { findViewById(R.id.tv_chall_password_warn) }
    private val tvPwLength: TextView by lazy { findViewById(R.id.tv_chall_password_length) }
    private val etVerify: EditText by lazy { findViewById(R.id.et_chall_verify) }
    private val tvVerifyWarn: TextView by lazy { findViewById(R.id.tv_chall_verify_warn) }
    private val btn: Button by lazy { findViewById(R.id.btn_chall_signup) }
    private val spinner: Spinner by lazy { findViewById(R.id.spnr_chall_mail) }

    private var okName = false
    private var okMail = false
    private var okPw = false
    private var okVerify = false

    private val pwWatcher by lazy {
        object : TextWatcher {
            private var beforePw = ""
            private var beforeCursor = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "before:: start: $start, count: $count, after: $after")
                Log.d(TAG, "$s/selection: ${etPw.selectionStart}, ${etPw.selectionEnd}")
                beforePw = etPw.text.toString()
                beforeCursor = start
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(
                    TAG,
                    "on:: start: $start, before: $before, count: $count"
                )
                Log.d(
                    TAG,
                    "$s/selection: ${etPw.selectionStart}, ${etPw.selectionEnd}"
                )
            }

            // 사용자의 입력을 막아버리는 것은 UX상 좋지 않다. 안내를 해주는 편이 좋다.
            // 한글은 조합때문에 입력받고 무시하기 어려울텐데, inputType textPassword 가 알아서 막아주네. 원표시₩도 막혀있네?
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "after:: $s/selection: ${etPw.selectionStart}, ${etPw.selectionEnd}")
//                val pw = etPw.text.toString()
                if (!pwInputPattern.matcher(etPw.text.toString()).matches()) {
                    // TODO: warn 바꾸는 쪽으로.
                    toastShort("입력할 수 없는 문자입니다.")
                    // TODO: InputFilter
                    etPw.removeTextChangedListener(this)
                    etPw.setText(beforePw)
                    etPw.setSelection(beforeCursor)
                    etPw.addTextChangedListener(this)
                    return
                }
                tvPwLength.text = "${etPw.text.length}/16"
                check(etPw)
                Log.d(TAG, "== back from check ==")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_challenge)

        // TODO: onCreate에 만든 변수가 어떻게 유지될 수 있는거지...?

        // 처음에 기본으로 첫번째꺼가 선택되어 있는 이유는 어레이어댑터라서 그런가?
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mails)
        etName.addTextChangedListener {
            check(etName)
        }
        etMail.addTextChangedListener {
            check(etMail)
        }
        etPw.addTextChangedListener(pwWatcher)
        etVerify.addTextChangedListener {
            check(etVerify)
        }
        val checkOnFocusOut = OnFocusChangeListener { v, hasFocus -> if (!hasFocus) check(v!!) }
        etName.onFocusChangeListener = checkOnFocusOut
        etMail.onFocusChangeListener = checkOnFocusOut
        etPw.onFocusChangeListener = checkOnFocusOut
        etVerify.onFocusChangeListener = checkOnFocusOut

        btn.setOnClickListener {
            if (spinner.isVisible) {
                intent.putExtra("id", "${etMail.text}@${spinner.selectedItem}")
            } else {
                intent.putExtra("id", "${etMail.text}@${etDomain.text}")
            }
            intent.putExtra("password", etPw.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        spinner.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    if (position == spinner.adapter.count - 1) {
                        etDomain.isVisible = true
                        spinner.isVisible = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    var checkCount = 0
    fun check(v: View) {
        Log.d(
            TAG,
            "v: ${
                v.toString().run { substring(length - 36, length - 15) }
            }, check count: ${++checkCount}"
        )
        when (v) {
            etName -> {
                if (etName.text.isEmpty()) {
                    tvNameWarn.text = "이름을 입력해주세요."
                    okName = false
                } else {
                    tvNameWarn.text = ""
                    okName = true
                }
            }

            etMail -> {
                if (etMail.text.isEmpty()) {
                    tvMailWarn.text = "이메일을 입력해주세요."
                    okMail = false
                } else {
                    tvMailWarn.text = ""
                    okMail = true
                }
            }

            etPw -> {
                val pw = etPw.text.toString()
                if (etPw.text.isEmpty()) {
                    tvPwWarn.text = "비밀번호를 입력해주세요."
                    okPw = false
                } else if (pw.length < 8) {
                    tvPwWarn.text = "비밀번호는 8자리 이상이어야 합니다."
                    okPw = false
                } else if (pw.length > 16) {
                    tvPwWarn.text = "비밀번호는 16자리 이하여야 합니다."
                    okPw = false
                } else if (!pwFinalPattern.matcher(pw).matches()) {
                    tvPwWarn.text = "영문, 숫자, 특수문자 혼합 8~16자"
                    okPw = false
                    // TODO: 동일한 문자를 많이 포함한 경우, 이름이나 메일 등 개인정보.
                } else {
                    tvPwWarn.text = ""
                    okPw = true
                }
                etName.setText(pw)  //ddd
            }

            etVerify -> {
                if (etVerify.text.contentEquals(etPw.text)) {
                    tvVerifyWarn.text = ""
                    okVerify = true
                } else {
                    tvVerifyWarn.text = "비밀번호가 일치하지 않습니다."
                    okVerify = false
                }
            }
        }

        btn.isEnabled = okName && okMail && okPw && okVerify
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}