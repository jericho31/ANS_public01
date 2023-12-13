package com.example.signinintroduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val data_id = intent.getStringExtra("id")
        val ivhome = findViewById<ImageView>(R.id.iv_home)
        val tvid = findViewById<TextView>(R.id.tv_home_id)
        val tvname = findViewById<TextView>(R.id.tv_home_name)
        val tvage = findViewById<TextView>(R.id.tv_home_age)
        val tvmbti = findViewById<TextView>(R.id.tv_home_mbti)
        tvid.setText("아이디: $data_id")
        if (data_id == "spa001") {
            ivhome.setImageResource(R.drawable.rtan)
            tvname.setText("이름: 스파르타코딩이")
            tvage.setText("나이: 23")
            tvmbti.setText("MBTI: ESTJ")
        }

        val btn = findViewById<Button>(R.id.btn_home_finish)
        btn.setOnClickListener {
            finish()
        }
    }
}