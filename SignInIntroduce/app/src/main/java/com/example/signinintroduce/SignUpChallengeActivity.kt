package com.example.signinintroduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class SignUpChallengeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_challenge)

        val mails = arrayOf("gmail.com", "kakao.com", "naver.com")
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mails)
        val spnr = findViewById<Spinner>(R.id.spnr_chall_mail)
        spnr.adapter = myAdapter

        val etName = findViewById<EditText>(R.id.et_chall_name)
        val tvNameWarn = findViewById<TextView>(R.id.tv_chall_name_warn)
        val etMail = findViewById<EditText>(R.id.et_chall_mail)
        val tvMailWarn = findViewById<TextView>(R.id.tv_chall_mail_warn)
        val etPw = findViewById<EditText>(R.id.et_chall_password)
        val tvPwWarn = findViewById<TextView>(R.id.tv_chall_password_warn)
        val etVerify = findViewById<EditText>(R.id.et_chall_verify)
        val tvVerifyWarn = findViewById<TextView>(R.id.tv_chall_verify_warn)
        val btn = findViewById<Button>(R.id.btn_chall_signup)

        etName.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tvNameWarn.text = ""
            else {
                if (etName.text.isEmpty()) tvNameWarn.text = "이름을 입력해주세요."
            }
        }
        etMail.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tvMailWarn.text = ""
            else {
                if (etMail.text.isEmpty()) tvMailWarn.text = "이메일을 입력해주세요."
            }
        }
        etPw.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tvPwWarn.text = ""
            else {
                if (etPw.text.isEmpty()) tvPwWarn.text = "비밀번호를 입력해주세요."
            }
        }
        etVerify.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tvVerifyWarn.text = ""
            else {
                if (etVerify.text.isEmpty()) tvVerifyWarn.text = "비밀번호를 한 번 더 입력해주세요."
            }
        }

        btn.setOnClickListener {
//            if (etName.text.isEmpty() || etMail.text.isEmpty() || etPw.text.isEmpty() || etVerify.text.isEmpty()) {
//                toastShort("입력되지 않은 정보가 있습니다.")
//                return@setOnClickListener
//            }

            intent.putExtra("id", etName.text.toString())
            intent.putExtra("password", etPw.text.toString())
            setResult(RESULT_OK, intent)

            finish()
        }
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}