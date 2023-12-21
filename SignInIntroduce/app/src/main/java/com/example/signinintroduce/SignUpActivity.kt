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

        val etName = findViewById<EditText>(R.id.et_up_name)
        val etId = findViewById<EditText>(R.id.et_up_id)
        val etPw = findViewById<EditText>(R.id.et_up_password)

        val btn = findViewById<Button>(R.id.btn_up_signUp)
        btn.setOnClickListener {
            if (etName.text.isEmpty() || etId.text.isEmpty() || etPw.text.isEmpty()) {
                toastShort(getString(R.string.toast_input_notyet))
                return@setOnClickListener
            }

            intent.putExtra(Extra.id, etId.text.toString())
            intent.putExtra(Extra.password, etPw.text.toString())
            setResult(RESULT_OK, intent)

            finish()
        }
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}