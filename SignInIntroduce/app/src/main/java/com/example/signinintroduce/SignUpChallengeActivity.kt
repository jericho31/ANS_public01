package com.example.signinintroduce

import androidx.appcompat.app.AppCompatActivity
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
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

// TODO: to xml
// 입력 길이도 정규식에서 체크할 수 있지만 메세지를 달리 하려면 체크하는 쪽에서 해야 함.
val pwInputRegex = """^[0-9a-zA-Z!"#${'$'}%&'()*+,-./:;<=>?@[₩]^_`{|}~]*$"""
val pwInputPattern = Pattern.compile(pwInputRegex)

class SignUpChallengeActivity : AppCompatActivity() {
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
    private val etVerify by lazy { findViewById<EditText>(R.id.et_chall_verify) }
    private val tvVerifyWarn by lazy { findViewById<TextView>(R.id.tv_chall_verify_warn) }
    private val btn by lazy { findViewById<Button>(R.id.btn_chall_signup) }

    private var password = ""
    private var posCursor = 0

    private val watcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("mine", "before: ${etPw.selectionStart}, ${etPw.selectionEnd}")
                posCursor = etPw.selectionStart
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(
                    "mine",
                    "on: ${p0}/ $p1, $p2, $p3 / ${etPw.selectionStart}, ${etPw.selectionEnd}"
                )
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("mine", "after: ${etPw.selectionStart}, ${etPw.selectionEnd}")
                check(etPw)
                Log.d("mine", "== back from check ==")
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
//            Log.d("mine", "etPw.addTextChangedListener: ${etPw.selectionStart}, ${etPw.selectionEnd}")
//            posCursor = etPw.selectionEnd
//            check(etPw)
//        }
        etPw.addTextChangedListener(watcher)
//        etPw.addTextChangedListener(object : TextWatcher {  // watcher로 만듦. remove, add 하려고.
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                Log.d("mine", "before: ${etPw.selectionStart}, ${etPw.selectionEnd}")
//                posCursor = etPw.selectionStart
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////                if (p0!![p2 - 1] == 'q') etPw.setText("")
//                Log.d(
//                    "mine",
//                    "on: ${p0}/ $p1, $p2, $p3 / ${etPw.selectionStart}, ${etPw.selectionEnd}"
//                )
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                Log.d("mine", "after: ${etPw.selectionStart}, ${etPw.selectionEnd}")
//                check(etPw)
//                Log.d("mine", "== back from check ==")
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
            "mine",
            "v: ${
                v.toString().run { substring(length - 36, length - 15) }
            }, check count: ${++checkCount}"
        )
        when (v) {
            etName -> {
                if (etName.text.isEmpty()) tvNameWarn.text = "이름을 입력해주세요."
                else tvNameWarn.text = ""
            }

            etMail -> {
                if (etMail.text.isEmpty()) tvMailWarn.text = "이메일을 입력해주세요."
                else tvMailWarn.text = ""
            }

            etPw -> {
                val pw = etPw.text.toString()
                if (etPw.text.isEmpty()) {
                    tvPwWarn.text = "비밀번호를 입력해주세요."
                } else if (!pwInputPattern.matcher(pw).matches()) {
                    toastShort("입력할 수 없는 문자입니다.")
                    // TODO: InputFilter
                    etPw.removeTextChangedListener(watcher)
                    etPw.setText(password)
                    etPw.addTextChangedListener(watcher)
                    etPw.setSelection(posCursor)
                    return
                } else if (pw.length < 8) {
                    tvPwWarn.text = "비밀번호는 8자리 이상이어야 합니다."
                } else if (pw.length > 16) {
                    tvPwWarn.text = "비밀번호는 16자리 이하여야 합니다."
                } else {
                    tvPwWarn.text = ""
                }
                password = pw
                etName.setText(password)
            }

            etVerify -> {
                if (etVerify.text.isEmpty()) {
                    tvVerifyWarn.text = "비밀번호를 한 번 더 입력해주세요."
                    return
                }
                if (!etVerify.text.contentEquals(etPw.text)) {
                    tvVerifyWarn.text = "비밀번호가 일치하지 않습니다."
                    return
                }
                tvVerifyWarn.text = ""
            }
        }
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}