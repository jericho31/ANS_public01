package com.example.signinintroduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

/** 랜덤으로 띄울 때 이전에 띄웠던 프로필이랑 다른 거 띄우기 위한 전역 변수 */
var randomNumber = -1

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
        when (data_id) {
            "default" -> {}
            "spa001" -> {
                ivhome.setImageResource(R.drawable.rtan)
                tvname.setText("이름: 스파르타코딩이")
                tvage.setText("나이: 23")
                tvmbti.setText("MBTI: ESTJ")
            }

            else -> {
                val before = randomNumber
                do randomNumber = Random.nextInt(5) while (randomNumber == before)
                when (randomNumber) {
                    0 -> {
                        ivhome.setImageResource(R.drawable.face1)
                        tvname.setText("이름: 잘생이")
                        tvage.setText("나이: 28")
                        tvmbti.setText("MBIT: ESFP")
                    }

                    1 -> {
                        ivhome.setImageResource(R.drawable.face2)
                        tvname.setText("이름: 평범이")
                        tvage.setText("나이: 24")
                        tvmbti.setText("MBIT: INFP")
                    }

                    2 -> {
                        ivhome.setImageResource(R.drawable.face3)
                        tvname.setText("이름: 곱슬이")
                        tvage.setText("나이: 30")
                        tvmbti.setText("MBIT: ENTJ")
                    }

                    3 -> {
                        ivhome.setImageResource(R.drawable.face4)
                        tvname.setText("이름: 털보")
                        tvage.setText("나이: 20")
                        tvmbti.setText("MBIT: INTP")
                    }

                    else -> {
                        ivhome.setImageResource(R.drawable.face5)
                        tvname.setText("이름: 활발이")
                        tvage.setText("나이: 25")
                        tvmbti.setText("MBIT: ENFP")
                    }
                }
            }
        }

        val btn = findViewById<Button>(R.id.btn_home_finish)
        btn.setOnClickListener {
            finish()
        }
    }
}