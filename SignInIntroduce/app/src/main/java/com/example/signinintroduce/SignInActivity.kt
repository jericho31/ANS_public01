package com.example.signinintroduce

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class SignInActivity : AppCompatActivity() {
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val etId = findViewById<EditText>(R.id.et_id)
                val etPw = findViewById<EditText>(R.id.et_password)
                etId.setText(it.data?.getStringExtra(Extra.id) ?: "")
                etPw.setText(it.data?.getStringExtra(Extra.password) ?: "")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

    fun doOnClick(v: View) {
        when (v.id) {
            R.id.btn_signin -> {
                val etId = findViewById<EditText>(R.id.et_id)
                val etPw = findViewById<EditText>(R.id.et_password)
                if (etId.text.isEmpty()) {
                    toastShort(getString(R.string.toast_enter_id))
                    return
                }
                if (etPw.text.isEmpty()) {
                    toastShort(getString(R.string.toast_enter_password))
                    return
                }
                toastShort(getString(R.string.toast_login_successed))

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(Extra.id, findViewById<EditText>(R.id.et_id).text.toString())
                startActivity(intent)
            }

            R.id.btn_signup -> {
//                val intent = Intent(this, SignUpActivity::class.java)
                val intent = Intent(this, SignUpChallengeActivity::class.java)
//                startActivity(intent)
                resultLauncher.launch(intent)
            }
        }
    }
}