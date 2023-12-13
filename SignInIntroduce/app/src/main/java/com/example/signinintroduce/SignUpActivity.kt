package com.example.signinintroduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val etname = findViewById<EditText>(R.id.et_up_name)
        val etid = findViewById<EditText>(R.id.et_up_id)
        val etpw = findViewById<EditText>(R.id.et_up_password)

        val btn = findViewById<Button>(R.id.btn_up_signUp)
        btn.setOnClickListener {
            if (etname.text.isEmpty() || etid.text.isEmpty() || etpw.text.isEmpty()) {
                toastShort("입력되지 않은 정보가 있습니다.")
                return@setOnClickListener
            }

            finish()
        }
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}