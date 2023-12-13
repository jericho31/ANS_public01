package com.example.signinintroduce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

    fun doOnClick(v: View) {
        when (v.id) {
            R.id.btn_signin -> {
                val etid = findViewById<EditText>(R.id.et_id)
                val etpw = findViewById<EditText>(R.id.et_password)
                if (etid.text.isEmpty()) {
                    toastShort("아이디를 입력해야 합니다.")
                    return
                }
                if (etpw.text.isEmpty()) {
                    toastShort("비밀번호를 입력해야 합니다.")
                    return
                }
                toastShort("로그인 성공")

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("id", findViewById<EditText>(R.id.et_id).text.toString())
                startActivity(intent)
            }

            R.id.btn_signup -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}