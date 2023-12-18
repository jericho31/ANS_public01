package com.example.signinintroduce

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

// TODO: to xml
// 입력 길이도 정규식에서 체크할 수 있지만 메세지를 달리 하려면 체크하는 쪽에서도 해야 함.
//val sample = """^[0-9a-zA-Z!@#$%^+\-=]*$"""  // 참고용
//val sample = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""  // 참고용
//val sample = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"  // 참고용
//val sample = "^(?=.*[A-Za-z])(?=.*[$@$!%*#?&.])[A-Za-z$@$!%*#?&.]{8,20}$"  // 참고용
//val sample = "^(?=.*[0-9])(?=.*[$@$!%*#?&.])[[0-9]$@$!%*#?&.]{8,20}$"  // 참고용
val pwInputRegex = """^[0-9a-zA-Z!"#$%&'()*+,-./:;<=>?@\[₩\]^_`{|}~]*$"""
val pwFinalRegex =
    """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!"#${'$'}%&'()*+,-./:;<=>?@\[₩\]^_`{|}~])[0-9a-zA-Z!"#${'$'}%&'()*+,-./:;<=>?@\[₩\]^_`{|}~]{8,16}$"""
val pwInputPattern = Pattern.compile(pwInputRegex)
val pwFinalPattern = Pattern.compile(pwFinalRegex)

class SignUpChallengeActivity : AppCompatActivity() {
    private val TAG = "mine"

    companion object {
        // TODO: 나중에 xml로.
        val mails = arrayOf("gmail.com", "kakao.com", "naver.com", "직접 입력")
    }

    private val etName by lazy { findViewById<EditText>(R.id.et_chall_name) }
    private val tvNameWarn by lazy { findViewById<TextView>(R.id.tv_chall_name_warn) }
    private val etMail by lazy { findViewById<EditText>(R.id.et_chall_mail) }
    private val tvMailWarn by lazy { findViewById<TextView>(R.id.tv_chall_mail_warn) }
    private val etPw by lazy { findViewById<EditText>(R.id.et_chall_password) }
    private val tvPwWarn by lazy { findViewById<TextView>(R.id.tv_chall_password_warn) }
    private val tvPwLength by lazy { findViewById<TextView>(R.id.tv_chall_password_length) }
    private val etVerify by lazy { findViewById<EditText>(R.id.et_chall_verify) }
    private val tvVerifyWarn by lazy { findViewById<TextView>(R.id.tv_chall_verify_warn) }
    private val btn by lazy { findViewById<Button>(R.id.btn_chall_signup) }

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

            // TODO: 사용자의 입력을 막아버리는 것은 UX상 좋지 않다. 안내를 해주는 편이 좋다.
            // 한글은 어려울텐데, inputType textPassword 가 알아서 막아주네. 원표시₩도 막혀있네?
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

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mails)
        val spnr = findViewById<Spinner>(R.id.spnr_chall_mail)
        spnr.adapter = myAdapter

        // TODO: onCreate에 만든 변수가 어떻게 유지될 수 있는거지...?

        etName.addTextChangedListener {
            check(etName)
        }
        etMail.addTextChangedListener {
            check(etMail)
        }
//        etPw.addTextChangedListener {  // 터짐
//            // 이미 바뀌고 찍힘.
//            Log.d(TAG, "etPw.addTextChangedListener: ${etPw.selectionStart}, ${etPw.selectionEnd}")
//            posCursor = etPw.selectionEnd
//            check(etPw)
//        }
        etPw.addTextChangedListener(pwWatcher)
//        etPw.addTextChangedListener(object : TextWatcher {  // watcher로 만듦. remove, add 하려고.
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                Log.d(TAG, "before: ${etPw.selectionStart}, ${etPw.selectionEnd}")
//                posCursor = etPw.selectionStart
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////                if (p0!![p2 - 1] == 'q') etPw.setText("")
//                Log.d(
//                    TAG,
//                    "on: ${p0}/ $p1, $p2, $p3 / ${etPw.selectionStart}, ${etPw.selectionEnd}"
//                )
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                Log.d(TAG, "after: ${etPw.selectionStart}, ${etPw.selectionEnd}")
//                check(etPw)
//                Log.d(TAG, "== back from check ==")
//            }
//        })
        etVerify.addTextChangedListener {
            check(etVerify)
        }
        etName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                check(v)
            }
        }
        etMail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                check(v)
            }
        }
        etPw.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                check(v)
            }
        }
        etVerify.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                check(v)
            }
        }

        btn.setOnClickListener {
//            if (etName.text.isEmpty() || etMail.text.isEmpty() || etPw.text.isEmpty() || etVerify.text.isEmpty()) {
//                toastShort("입력되지 않은 정보가 있습니다.")
//                return@setOnClickListener
//            }

            intent.putExtra("id", "${etMail.text}@${spnr.selectedItem}")
            intent.putExtra("password", etPw.text.toString())
            setResult(RESULT_OK, intent)

            finish()
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